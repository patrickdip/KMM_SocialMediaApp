package com.dipumba.ytsocialapp.android.home

import androidx.compose.runtime.Composable
import com.dipumba.ytsocialapp.android.destinations.PostDetailDestination
import com.dipumba.ytsocialapp.android.destinations.ProfileDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination(start = true)
fun Home(
    navigator: DestinationsNavigator
) {
    val viewModel: HomeScreenViewModel = koinViewModel()

    HomeScreen(
        onUserClick = {},
        onFollowButtonClick = { _, _ -> },
        onPostClick = {post -> navigator.navigate(PostDetailDestination(post.id)) },
        onProfileClick = { navigator.navigate(ProfileDestination(it))},
        onLikeClick = {},
        onCommentClick = {},
        refreshData = {viewModel.fetchData()},
        onBoardingUiState = viewModel.onBoardingUiState,
        homePostsUiState = viewModel.homePostsUiState,
        onBoardingFinish = {}
    )
}