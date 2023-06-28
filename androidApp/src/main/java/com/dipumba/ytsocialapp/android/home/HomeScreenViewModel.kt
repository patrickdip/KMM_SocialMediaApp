package com.dipumba.ytsocialapp.android.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipumba.ytsocialapp.android.common.dummy_data.FollowsUser
import com.dipumba.ytsocialapp.android.common.dummy_data.Post
import com.dipumba.ytsocialapp.android.common.dummy_data.samplePosts
import com.dipumba.ytsocialapp.android.common.dummy_data.sampleUsers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreenViewModel: ViewModel() {

    var onBoardingUiState by mutableStateOf(OnBoardingUiState())
        private set

    var homePostsUiState by mutableStateOf(HomePostsUiState())
        private set


    init {
        fetchData()
    }


    fun fetchData(){
        onBoardingUiState = onBoardingUiState.copy(isLoading = true)
        homePostsUiState = homePostsUiState.copy(isLoading = true)

        viewModelScope.launch {
            delay(1000)

            onBoardingUiState = onBoardingUiState.copy(
                isLoading = false,
                followableUsers = sampleUsers,
                shouldShowOnBoarding = true
            )
            homePostsUiState = homePostsUiState.copy(
                isLoading = false,
                posts = samplePosts
            )
        }
    }

}

data class OnBoardingUiState(
    val shouldShowOnBoarding: Boolean = false,
    val isLoading: Boolean = false,
    val followableUsers: List<FollowsUser> = listOf(),
    val loadingErrorMessage: String? = null
)

data class HomePostsUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = listOf(),
    val loadingErrorMessage: String? = null
)