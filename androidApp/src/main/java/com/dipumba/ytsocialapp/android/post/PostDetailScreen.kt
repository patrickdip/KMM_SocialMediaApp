package com.dipumba.ytsocialapp.android.post

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dipumba.ytsocialapp.android.R
import com.dipumba.ytsocialapp.android.common.components.CommentListItem
import com.dipumba.ytsocialapp.android.common.components.PostListItem
import com.dipumba.ytsocialapp.android.common.dummy_data.sampleComments
import com.dipumba.ytsocialapp.android.common.dummy_data.samplePosts
import com.dipumba.ytsocialapp.android.common.theming.LargeSpacing
import com.dipumba.ytsocialapp.android.common.theming.MediumSpacing
import com.dipumba.ytsocialapp.android.common.theming.SocialAppTheme
import com.dipumba.ytsocialapp.android.common.util.Constants

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    postUiState: PostUiState,
    commentsUiState: CommentsUiState,
    postId: Long,
    onUiAction: (PostDetailUiAction) -> Unit
) {
    val listState = rememberLazyListState()

    val shouldFetchMoreComments by remember {
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

    if (postUiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if(postUiState.post != null) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colors.surface),
            state = listState
        ) {
            item(key = "post") {
                PostListItem(
                    post = postUiState.post,
                    onPostClick = {},
                    onProfileClick = {},
                    onLikeClick = { onUiAction(PostDetailUiAction.LikeOrDislikePostAction(it))},
                    onCommentClick = {},
                    isDetailScreen = true
                )
            }

            item(key = "comments_header") {
                CommentsHeaderSection(
                    onAddCommentClick = {}
                )
            }

            items(
                items = commentsUiState.comments,
                key = { comment -> comment.commentId }
            ) {
                Divider()
                CommentListItem(
                    comment = it,
                    onProfileClick = {},
                    onMoreIconClick = {}
                )
            }

            if(commentsUiState.isLoading){
                item(key = Constants.LOADING_MORE_ITEM_KEY) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = MediumSpacing,
                                horizontal = LargeSpacing
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }else{
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Column {
                Text(
                    text = stringResource(id = R.string.loading_error_message),
                    style = MaterialTheme.typography.caption
                )
                OutlinedButton(onClick = { onUiAction(PostDetailUiAction.FetchPostAction(postId)) }) {
                    Text(text = stringResource(id = R.string.retry_button_text))
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        onUiAction(PostDetailUiAction.FetchPostAction(postId))
    }

    LaunchedEffect(key1 = shouldFetchMoreComments) {
        if (shouldFetchMoreComments && !commentsUiState.endReached) {
            onUiAction(PostDetailUiAction.LoadMoreCommentsAction)
        }
    }
}


@Composable
private fun CommentsHeaderSection(
    modifier: Modifier = Modifier,
    onAddCommentClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = LargeSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.comments_label),
            style = MaterialTheme.typography.subtitle1,
            modifier = modifier.weight(1f)
        )

        OutlinedButton(onClick = onAddCommentClick) {
            Text(text = stringResource(id = R.string.add_comment_button_label))
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PostDetailPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colors.surface) {
            PostDetailScreen(
                postUiState = PostUiState(
                    isLoading = false,
                    post = samplePosts.first().toDomainPost()
                ),
                commentsUiState = CommentsUiState(
                    isLoading = false,
                    comments = sampleComments.map { it.toDomainComment() }
                ),
                postId = 1,
                onUiAction = {}
            )
        }
    }
}