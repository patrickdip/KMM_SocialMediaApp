package com.dipumba.ytsocialapp.android.account.follows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dipumba.ytsocialapp.android.common.dummy_data.SampleFollowsUser
import com.dipumba.ytsocialapp.android.common.dummy_data.sampleUsers
import com.dipumba.ytsocialapp.android.common.util.Constants
import com.dipumba.ytsocialapp.android.common.util.DefaultPagingManager
import com.dipumba.ytsocialapp.android.common.util.PagingManager
import com.dipumba.ytsocialapp.common.domain.model.FollowsUser
import com.dipumba.ytsocialapp.follows.domain.usecase.GetFollowsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FollowsViewModel(
    private val getFollowsUseCase: GetFollowsUseCase
) : ViewModel(){

    var uiState by mutableStateOf(FollowsUiState())
        private set

    private lateinit var pagingManager: PagingManager<FollowsUser>

    private fun fetchFollows(userId: Long, followsType: Int){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000)
            if (!::pagingManager.isInitialized) {
                pagingManager = createPagingManager(userId, followsType)
                pagingManager.loadItems()
            }
        }
    }

    private fun createPagingManager(userId: Long, followsType: Int): PagingManager<FollowsUser> {
        return DefaultPagingManager(
            onRequest = { page ->
                getFollowsUseCase(
                    userId = userId,
                    page = page,
                    pageSize = Constants.DEFAULT_REQUEST_PAGE_SIZE,
                    followsType = followsType
                )
            },
            onSuccess = { follows, _ ->
                uiState = uiState.copy(
                    isLoading = false,
                    followsUsers = uiState.followsUsers + follows,
                    endReached = follows.size < Constants.DEFAULT_REQUEST_PAGE_SIZE
                )
            },
            onError = { message, _ ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = message
                )
            },
            onLoadStateChange = {
                uiState = uiState.copy(isLoading = it)
            }
        )
    }

    private fun loadMoreFollows() {
        if (uiState.endReached) return
        viewModelScope.launch {
            pagingManager.loadItems()
        }
    }

    fun onUiAction(action: FollowsUiAction) {
        when (action) {
            is FollowsUiAction.FetchFollowsAction -> {
                fetchFollows(action.userId, action.followsType)
            }
            is FollowsUiAction.LoadMoreFollowsAction -> {
                loadMoreFollows()
            }
        }
    }
}

data class FollowsUiState(
    val isLoading: Boolean = false,
    val followsUsers: List<FollowsUser> = listOf(),
    val errorMessage: String? = null,
    val endReached: Boolean = false
)

sealed interface FollowsUiAction{
    data class FetchFollowsAction(val userId: Long, val followsType: Int): FollowsUiAction
    data object LoadMoreFollowsAction: FollowsUiAction
}