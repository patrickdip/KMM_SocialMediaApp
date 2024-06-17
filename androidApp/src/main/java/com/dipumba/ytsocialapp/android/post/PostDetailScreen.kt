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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.dipumba.ytsocialapp.android.common.theming.SocialAppTheme

@Composable
fun PostDetailScreen(
    modifier: Modifier = Modifier,
    postUiState: PostUiState,
    commentsUiState: CommentsUiState,
    fetchData: () -> Unit
) {
    if (postUiState.isLoading && commentsUiState.isLoading) {
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
                .background(color = MaterialTheme.colors.surface)
        ) {
            item(key = "post") {
                PostListItem(
                    post = postUiState.post.toDomainPost(),
                    onPostClick = {},
                    onProfileClick = {},
                    onLikeClick = {},
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
                items = sampleComments,
                key = { comment -> comment.id }
            ) {
                Divider()
                CommentListItem(
                    comment = it,
                    onProfileClick = {},
                    onMoreIconClick = {}
                )
            }
        }
    }else{
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Column {
                Text(
                    text = stringResource(id = R.string.loading_error_message),
                    style = MaterialTheme.typography.caption
                )
                OutlinedButton(onClick = fetchData) {
                    Text(text = stringResource(id = R.string.retry_button_text))
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        fetchData()
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
                    post = samplePosts.first()
                ),
                commentsUiState = CommentsUiState(
                    isLoading = false,
                    comments = sampleComments
                ),
                fetchData = {}
            )
        }
    }
}