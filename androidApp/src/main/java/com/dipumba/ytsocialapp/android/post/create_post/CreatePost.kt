package com.dipumba.ytsocialapp.android.post.create_post

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun CreatePost(navigator: DestinationsNavigator) {
    val viewModel: CreatePostViewModel = koinViewModel()

    CreatePostScreen(
        modifier = Modifier,
        createPostUiState = viewModel.uiState,
        onPostCreated = { navigator.popBackStack() },
        onUiAction = viewModel::onUiAction
    )
}