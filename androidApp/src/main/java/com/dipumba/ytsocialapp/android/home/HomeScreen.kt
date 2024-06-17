package com.dipumba.ytsocialapp.android.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.dipumba.ytsocialapp.android.R
import com.dipumba.ytsocialapp.android.common.components.PostListItem
import com.dipumba.ytsocialapp.android.common.dummy_data.SamplePost
import com.dipumba.ytsocialapp.android.common.theming.LargeSpacing
import com.dipumba.ytsocialapp.android.common.theming.MediumSpacing
import com.dipumba.ytsocialapp.android.common.theming.SocialAppTheme
import com.dipumba.ytsocialapp.android.common.util.Constants
import com.dipumba.ytsocialapp.android.home.onboarding.OnBoardingSection
import com.dipumba.ytsocialapp.common.domain.model.Post

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBoardingUiState: OnBoardingUiState,
    postsFeedUiState: PostsFeedUiState,
    homeRefreshState: HomeRefreshState,
    onUiAction: (HomeUiAction) -> Unit,
    onProfileNavigation: (userId: Long) -> Unit,
    onPostDetailNavigation: (Post) -> Unit
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeRefreshState.isRefreshing,
        onRefresh = { onUiAction(HomeUiAction.RefreshAction) }
    )

    val listState = rememberLazyListState()
    val shouldFetchMorePosts by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (layoutInfo.totalItemsCount == 0) {
                false
            }else{
                val lastVisibleItem = visibleItemsInfo.last()
                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = listState
        ) {
            if (onBoardingUiState.shouldShowOnBoarding){
                item {
                    OnBoardingSection(
                        users = onBoardingUiState.followableUsers,
                        onUserClick = {onProfileNavigation(it.id)},
                        onFollowButtonClick = {_, user ->
                            onUiAction(
                                HomeUiAction.FollowUserAction(
                                    user
                                )
                            )
                        },
                        onBoardingFinish = { onUiAction(HomeUiAction.RemoveOnboardingAction) }
                    )

                    Text(
                        text = stringResource(id = R.string.trending_posts_title),
                        style = MaterialTheme.typography.subtitle1,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(bottom = LargeSpacing),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(items = postsFeedUiState.posts, key = { post -> post.postId }) {post ->
                PostListItem(
                    post = post,
                    onPostClick = { onPostDetailNavigation(it) },
                    onProfileClick = { onProfileNavigation(it) },
                    onLikeClick = { onUiAction(HomeUiAction.PostLikeAction(it)) },
                    onCommentClick = { onPostDetailNavigation(it) }
                )
            }

            if (postsFeedUiState.isLoading && postsFeedUiState.posts.isNotEmpty()) {
                item(key = Constants.LOADING_MORE_ITEM_KEY) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = MediumSpacing, horizontal = LargeSpacing),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = homeRefreshState.isRefreshing,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter)
        )

        LaunchedEffect(key1 = shouldFetchMorePosts) {
            if (shouldFetchMorePosts && !postsFeedUiState.endReached) {
                onUiAction(HomeUiAction.LoadMorePostsAction)
            }
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            HomeScreen(
                onBoardingUiState = OnBoardingUiState(),
                postsFeedUiState = PostsFeedUiState(),
                homeRefreshState = HomeRefreshState(),
                onPostDetailNavigation = {},
                onProfileNavigation = {},
                onUiAction = {}
            )
        }
    }
}