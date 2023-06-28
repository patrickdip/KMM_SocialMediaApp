package com.dipumba.ytsocialapp.android.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.dipumba.ytsocialapp.android.R
import com.dipumba.ytsocialapp.android.common.components.PostListItem
import com.dipumba.ytsocialapp.android.common.dummy_data.FollowsUser
import com.dipumba.ytsocialapp.android.common.dummy_data.Post
import com.dipumba.ytsocialapp.android.common.theming.LargeSpacing
import com.dipumba.ytsocialapp.android.common.theming.SocialAppTheme
import com.dipumba.ytsocialapp.android.home.onboarding.OnBoardingSection

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBoardingUiState: OnBoardingUiState,
    homePostsUiState: HomePostsUiState,
    onUserClick: (FollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, FollowsUser) -> Unit,
    onPostClick: (Post) -> Unit,
    onProfileClick: (Int) -> Unit,
    onLikeClick: (String) -> Unit,
    onCommentClick: (String) -> Unit,
    refreshData: () -> Unit,
    onBoardingFinish: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = onBoardingUiState.isLoading && homePostsUiState.isLoading,
        onRefresh = refreshData
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            if (onBoardingUiState.shouldShowOnBoarding){
                item {
                    OnBoardingSection(
                        users = onBoardingUiState.followableUsers,
                        onUserClick = onUserClick,
                        onFollowButtonClick = onFollowButtonClick,
                        onBoardingFinish = onBoardingFinish
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

            items(items = homePostsUiState.posts, key = { post -> post.id }) {
                PostListItem(
                    post = it,
                    onPostClick = onPostClick,
                    onProfileClick = onProfileClick,
                    onLikeClick = onLikeClick,
                    onCommentClick = onCommentClick
                )
            }
        }

        PullRefreshIndicator(
            refreshing = onBoardingUiState.isLoading && homePostsUiState.isLoading,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter)
        )
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            HomeScreen(
                onBoardingUiState = OnBoardingUiState(),
                homePostsUiState = HomePostsUiState(),
                onUserClick = {},
                onFollowButtonClick = { _, _ -> },
                onPostClick = {},
                onProfileClick = {},
                onLikeClick = {},
                onCommentClick = {},
                refreshData = {},
                onBoardingFinish = {}
            )
        }
    }
}