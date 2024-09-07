package com.dipumba.ytsocialapp.android.account.follows

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dipumba.ytsocialapp.android.common.components.loadingMoreItem

@Composable
fun FollowsScreen(
    modifier: Modifier = Modifier,
    uiState: FollowsUiState,
    userId: Long,
    followsType: Int,
    onUiAction: (FollowsUiAction) -> Unit,
    onProfileNavigation: (userId: Long) -> Unit
) {

    val listState = rememberLazyListState()

    val shouldFetchMoreFollows by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.last()
                (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = listState
        ){
            items(items = uiState.followsUsers, key = { user -> user.id}){
                FollowsListItem(name = it.name, bio = it.bio, imageUrl = it.imageUrl) {
                    onProfileNavigation(it.id)
                }
            }

            if (uiState.isLoading && uiState.followsUsers.isNotEmpty() && !uiState.endReached){
                loadingMoreItem()
            }
        }

        if (uiState.isLoading && uiState.followsUsers.isEmpty()){
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(
        key1 = Unit,
        block = { onUiAction(FollowsUiAction.FetchFollowsAction(userId, followsType)) }
    )

    LaunchedEffect(key1 = shouldFetchMoreFollows){
        if (shouldFetchMoreFollows && !uiState.endReached){
            onUiAction(FollowsUiAction.LoadMoreFollowsAction)
        }
    }
}