package com.dipumba.ytsocialapp.android.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipumba.ytsocialapp.android.common.dummy_data.Comment
import com.dipumba.ytsocialapp.android.common.dummy_data.SamplePost
import com.dipumba.ytsocialapp.android.common.dummy_data.sampleComments
import com.dipumba.ytsocialapp.android.common.dummy_data.samplePosts
import com.dipumba.ytsocialapp.android.common.util.Constants
import com.dipumba.ytsocialapp.android.common.util.DefaultPagingManager
import com.dipumba.ytsocialapp.android.common.util.Event
import com.dipumba.ytsocialapp.android.common.util.EventBus
import com.dipumba.ytsocialapp.android.common.util.PagingManager
import com.dipumba.ytsocialapp.common.domain.model.Post
import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.domain.model.PostComment
import com.dipumba.ytsocialapp.post.domain.usecase.GetPostCommentsUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.GetPostUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.LikeOrDislikePostUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val likeOrDislikePostUseCase: LikeOrDislikePostUseCase
): ViewModel() {
    var postUiState by mutableStateOf(PostUiState())
        private set

    var commentsUiState by mutableStateOf(CommentsUiState())
        private set

    private lateinit var pagingManager: PagingManager<PostComment>

    init {
        EventBus.events
            .onEach {
                when (it) {
                    is Event.PostUpdated -> updatePost(it.post)
                }
            }
            .launchIn(viewModelScope)
    }


    private fun fetchData(postId: Long){
        viewModelScope.launch {
            delay(500)

            when (val result = getPostUseCase(postId = postId)) {
                is Result.Success -> {
                    postUiState = postUiState.copy(
                        isLoading = false,
                        post = result.data
                    )
                    fetchPostComments(postId)
                }

                is Result.Error -> {
                    postUiState = postUiState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }

    private suspend fun fetchPostComments(postId: Long) {
        if (commentsUiState.isLoading || commentsUiState.comments.isNotEmpty()) {
            return
        }

        if (!::pagingManager.isInitialized) {
            pagingManager = createPagingManager(postId = postId)
        }
        pagingManager.loadItems()
    }

    private fun loadMoreComments() {
        if (commentsUiState.endReached) return
        viewModelScope.launch { pagingManager.loadItems() }
    }

    private fun createPagingManager(postId: Long): PagingManager<PostComment>{
        return DefaultPagingManager(
            onRequest = { page ->
                getPostCommentsUseCase(
                    postId = postId,
                    page = page,
                    pageSize = Constants.DEFAULT_REQUEST_COMMENTS_PAGE_SIZE
                )
            },
            onSuccess = { comments, _ ->
                commentsUiState = commentsUiState.copy(
                    comments = commentsUiState.comments + comments,
                    endReached = comments.size < Constants.DEFAULT_REQUEST_COMMENTS_PAGE_SIZE
                )
            },
            onError = { message, _ ->
                commentsUiState = commentsUiState.copy(
                    errorMessage = message
                )
            },
            onLoadStateChange = { isLoading ->
                commentsUiState = commentsUiState.copy(isLoading = isLoading)
            }
        )
    }

    private fun likeOrDislikePost(post: Post) {
        viewModelScope.launch {
            val count = if (post.isLiked) -1 else +1
            val updatedPost = post.copy(
                isLiked = !post.isLiked,
                likesCount = post.likesCount.plus(count)
            )

            updatePost(updatedPost)

            val result = likeOrDislikePostUseCase(
                post = post,
            )

            when (result) {
                is Result.Error -> {
                    //if the operation fails, then rollback
                    updatePost(post)
                }

                is Result.Success -> {
                    EventBus.send(Event.PostUpdated(updatedPost))
                }
            }
        }
    }

    private fun updatePost(post: Post){
        postUiState = postUiState.copy(
            post = post
        )
    }

    fun onUiAction(action: PostDetailUiAction){
        when(action){
            is PostDetailUiAction.FetchPostAction -> {
                fetchData(action.postId)
            }
            is PostDetailUiAction.LoadMoreCommentsAction -> {
                loadMoreComments()
            }
            is PostDetailUiAction.LikeOrDislikePostAction -> {
                likeOrDislikePost(action.post)
            }
        }
    }
}

data class PostUiState(
    val isLoading: Boolean = true,
    val post: Post? = null,
    val errorMessage: String? = null
)

data class CommentsUiState(
    val isLoading: Boolean = false,
    val comments: List<PostComment> = listOf(),
    val errorMessage: String? = null,
    val endReached: Boolean = false
)

sealed interface PostDetailUiAction{
    data class FetchPostAction(val postId: Long): PostDetailUiAction
    data object LoadMoreCommentsAction: PostDetailUiAction
    data class LikeOrDislikePostAction(val post: Post): PostDetailUiAction
}