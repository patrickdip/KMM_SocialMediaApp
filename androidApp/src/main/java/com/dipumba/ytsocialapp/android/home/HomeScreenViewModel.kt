package com.dipumba.ytsocialapp.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipumba.ytsocialapp.account.domain.model.Profile
import com.dipumba.ytsocialapp.android.common.util.Constants
import com.dipumba.ytsocialapp.android.common.util.DefaultPagingManager
import com.dipumba.ytsocialapp.android.common.util.Event
import com.dipumba.ytsocialapp.android.common.util.EventBus
import com.dipumba.ytsocialapp.android.common.util.PagingManager
import com.dipumba.ytsocialapp.common.domain.model.FollowsUser
import com.dipumba.ytsocialapp.common.domain.model.Post
import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.follows.domain.usecase.FollowOrUnfollowUseCase
import com.dipumba.ytsocialapp.follows.domain.usecase.GetFollowableUsersUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.GetPostsUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.LikeOrDislikePostUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getFollowableUsersUseCase: GetFollowableUsersUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val likePostUseCase: LikeOrDislikePostUseCase
): ViewModel() {

    var onBoardingUiState by mutableStateOf(OnBoardingUiState())
        private set

    var postsFeedUiState by mutableStateOf(PostsFeedUiState())
        private set

    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set

    private val pagingManager by lazy { createPagingManager() }


    init {
        fetchData()

        EventBus.events
            .onEach {
                when (it) {
                    is Event.PostUpdated -> updatePost(it.post)
                    is Event.ProfileUpdated -> updateCurrentUserPostsProfileData(it.profile)
                    is Event.PostCreated -> insertNewPost(it.post)
                }
            }.launchIn(viewModelScope)
    }


    private fun fetchData(){
        homeRefreshState = homeRefreshState.copy(isRefreshing = true)

        viewModelScope.launch {
            delay(1000)

            val onboardingDeferred = async {
                getFollowableUsersUseCase()
            }

            pagingManager.apply {
                reset()
                loadItems()
            }
            handleOnBoardingResult(onboardingDeferred.await())
            homeRefreshState = homeRefreshState.copy(isRefreshing = false)
        }
    }

    private fun createPagingManager(): PagingManager<Post>{
        return DefaultPagingManager(
            onRequest = {page ->
                getPostsUseCase(page, Constants.DEFAULT_REQUEST_PAGE_SIZE)
            },
            onSuccess = {posts, page ->
                postsFeedUiState = if (posts.isEmpty()){
                    postsFeedUiState.copy(endReached = true)
                }else{
                    if (page == Constants.INITIAL_PAGE_NUMBER){
                        postsFeedUiState = postsFeedUiState.copy(posts = emptyList())
                    }
                    postsFeedUiState.copy(
                        posts = postsFeedUiState.posts + posts,
                        endReached = posts.size < Constants.DEFAULT_REQUEST_PAGE_SIZE
                    )
                }
            },
            onError = {cause, page ->
                if (page == Constants.INITIAL_PAGE_NUMBER){
                    homeRefreshState = homeRefreshState.copy(
                        refreshErrorMessage = cause
                    )
                }else{
                    postsFeedUiState = postsFeedUiState.copy(
                        loadingErrorMessage = cause
                    )
                }
            },
            onLoadStateChange = {isLoading ->
                postsFeedUiState = postsFeedUiState.copy(
                    isLoading = isLoading
                )
            }
        )
    }

    private fun loadMorePosts() {
        if (postsFeedUiState.endReached) return
        viewModelScope.launch {
            pagingManager.loadItems()
        }
    }

    private fun handleOnBoardingResult(result: Result<List<FollowsUser>>) {
        when (result) {
            is Result.Error -> Unit

            is Result.Success -> {
                result.data?.let { followsUsers ->
                    onBoardingUiState = onBoardingUiState.copy(
                        shouldShowOnBoarding = followsUsers.isNotEmpty(),
                        followableUsers = followsUsers
                    )
                }
            }
        }
    }

    private fun followUser(followsUser: FollowsUser) {
        viewModelScope.launch {
            val result = followOrUnfollowUseCase(
                followedUserId = followsUser.id,
                shouldFollow = !followsUser.isFollowing
            )

            onBoardingUiState = onBoardingUiState.copy(
                followableUsers = onBoardingUiState.followableUsers.map {
                    if (it.id == followsUser.id) {
                        it.copy(isFollowing = !followsUser.isFollowing)
                    } else it
                }
            )

            when (result) {
                is Result.Error -> {
                    onBoardingUiState = onBoardingUiState.copy(
                        followableUsers = onBoardingUiState.followableUsers.map {
                            if (it.id == followsUser.id) {
                                it.copy(isFollowing = followsUser.isFollowing)
                            } else it
                        }
                    )
                }

                is Result.Success -> Unit
            }
        }
    }

    private fun dismissOnboarding() {
        val hasFollowing = onBoardingUiState.followableUsers.any { it.isFollowing }
        if (!hasFollowing) {
            //TODO tell the user they need to follow at least one person
        } else {
            onBoardingUiState =
                onBoardingUiState.copy(shouldShowOnBoarding = false, followableUsers = emptyList())
            fetchData()
        }
    }

    private fun likeOrUnlikePost(post: Post) {
        viewModelScope.launch {
            val count = if (post.isLiked) -1 else +1
            postsFeedUiState = postsFeedUiState.copy(
                posts = postsFeedUiState.posts.map {
                    if (it.postId == post.postId) {
                        it.copy(
                            isLiked = !post.isLiked,
                            likesCount = post.likesCount.plus(count)
                        )
                    } else it
                }
            )

            val result = likePostUseCase(
                post = post,
            )

            when (result) {
                is Result.Error -> {
                    updatePost(post)
                }

                is Result.Success -> Unit
            }
        }
    }

    private fun updatePost(post: Post) {
        postsFeedUiState = postsFeedUiState.copy(
            posts = postsFeedUiState.posts.map {
                if (it.postId == post.postId) post else it
            }
        )
    }

    private fun insertNewPost(post: Post){
        postsFeedUiState = postsFeedUiState.copy(
            posts = listOf(post) + postsFeedUiState.posts
        )
    }

    private fun updateCurrentUserPostsProfileData(profile: Profile) {
        postsFeedUiState = postsFeedUiState.copy(
            posts = postsFeedUiState.posts.map {
                if (it.userId == profile.id) {//should use it.isOwnComment
                    it.copy(
                        userName = profile.name,
                        userImageUrl = profile.imageUrl
                    )
                } else {
                    it
                }
            }
        )
    }

    fun onUiAction(uiAction: HomeUiAction) {
        when (uiAction) {
            is HomeUiAction.FollowUserAction -> followUser(uiAction.user)
            HomeUiAction.LoadMorePostsAction -> loadMorePosts()
            is HomeUiAction.PostLikeAction -> likeOrUnlikePost(uiAction.post)
            HomeUiAction.RefreshAction -> fetchData()
            HomeUiAction.RemoveOnboardingAction -> dismissOnboarding()
        }
    }

}

data class HomeRefreshState(
    val isRefreshing: Boolean = false,
    val refreshErrorMessage: String? = null
)

data class OnBoardingUiState(
    val shouldShowOnBoarding: Boolean = false,
    val followableUsers: List<FollowsUser> = listOf()
)

data class PostsFeedUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = listOf(),
    val loadingErrorMessage: String? = null,
    val endReached: Boolean = false
)

sealed interface HomeUiAction {
    data class FollowUserAction(val user: FollowsUser) : HomeUiAction
    data class PostLikeAction(val post: Post) : HomeUiAction
    data object RemoveOnboardingAction : HomeUiAction
    data object RefreshAction : HomeUiAction
    data object LoadMorePostsAction : HomeUiAction
}