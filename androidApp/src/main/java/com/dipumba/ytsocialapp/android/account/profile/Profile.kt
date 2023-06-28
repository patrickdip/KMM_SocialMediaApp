package com.dipumba.ytsocialapp.android.account.profile

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun Profile(
    navigator: DestinationsNavigator,
    userId: Int
) {
    val viewModel: ProfileViewModel = koinViewModel()

    ProfileScreen(
        userInfoUiState = viewModel.userInfoUiState,
        postsUiState = viewModel.profilePostsUiState,
        onButtonClick = { /*TODO*/ },
        onFollowersClick = { /*TODO*/ },
        onFollowingClick = { /*TODO*/ },
        onPostClick = {/*TODO*/ },
        onLikeClick = {/*TODO*/ },
        onCommentClick = {/*TODO*/ },
        fetchData = { viewModel.fetchProfile(userId) }
    )
}