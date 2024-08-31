package com.dipumba.ytsocialapp.android.post

import androidx.compose.runtime.Composable
import com.dipumba.ytsocialapp.android.destinations.ProfileDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun PostDetail(
    navigator: DestinationsNavigator,
    postId: Long
) {
    val viewModel: PostDetailViewModel = koinViewModel()

    PostDetailScreen(
        postUiState = viewModel.postUiState,
        commentsUiState = viewModel.commentsUiState,
        postId = postId,
        onProfileNavigation = {
            navigator.navigate(ProfileDestination(it))
        },
        onUiAction = viewModel::onUiAction
    )
}