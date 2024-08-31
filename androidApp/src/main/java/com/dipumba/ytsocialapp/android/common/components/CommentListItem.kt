package com.dipumba.ytsocialapp.android.common.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dipumba.ytsocialapp.android.R
import com.dipumba.ytsocialapp.android.common.dummy_data.Comment
import com.dipumba.ytsocialapp.android.common.dummy_data.sampleComments
import com.dipumba.ytsocialapp.android.common.theming.DarkGray
import com.dipumba.ytsocialapp.android.common.theming.LargeSpacing
import com.dipumba.ytsocialapp.android.common.theming.LightGray
import com.dipumba.ytsocialapp.android.common.theming.MediumSpacing
import com.dipumba.ytsocialapp.android.common.theming.SocialAppTheme
import com.dipumba.ytsocialapp.android.common.util.toCurrentUrl
import com.dipumba.ytsocialapp.post.domain.model.PostComment


@Composable
fun CommentListItem(
    modifier: Modifier = Modifier,
    comment: PostComment,
    onProfileClick: (Long) -> Unit,
    onMoreIconClick: (PostComment) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(all = LargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircleImage(
            modifier = modifier.size(30.dp),
            url = comment.userImageUrl?.toCurrentUrl(),
            onClick = { onProfileClick(comment.userId) }
        )

        Column(
            modifier = modifier
                .weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    MediumSpacing
                )
            ) {
                Text(
                    text = comment.userName,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = modifier.alignByBaseline()
                )

                Text(
                    text = comment.createdAt,
                    style = MaterialTheme.typography.caption.copy(fontSize = 11.sp),
                    color = if (MaterialTheme.colors.isLight) {
                        LightGray
                    } else {
                        DarkGray
                    },
                    modifier = modifier.alignByBaseline().weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.round_more_horizontal),
                    contentDescription = null,
                    tint = if (MaterialTheme.colors.isLight) {
                        LightGray
                    } else {
                        DarkGray
                    },
                    modifier = modifier.clickable { onMoreIconClick(comment) }
                )
            }

            Text(
                text = comment.content,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CommentListItemPreview() {
    SocialAppTheme {
        Surface(color = MaterialTheme.colors.surface) {
            CommentListItem(
                comment = sampleComments.first().toDomainComment(),
                onProfileClick = {},
                onMoreIconClick = {}
            )
        }
    }
}


@Composable
fun CommentItemLayout(
    modifier: Modifier,
    image: @Composable () -> Unit,
    username: @Composable () -> Unit,
    date: @Composable () -> Unit,
    moreIcon: @Composable () -> Unit,
    commentText: @Composable () -> Unit
) {
    Layout(
        contents = listOf(
            image,
            username,
            date,
            moreIcon,
            commentText
        ),
        modifier = modifier
            .padding(
                top = LargeSpacing,
                bottom = LargeSpacing,
                start = LargeSpacing,
                end = MediumSpacing
            )
    ) { (imageMeasurables,
            usernameMeasurables,
            dateMeasurables,
            moreIconMeasurables,
            commentTextMeasurables),
        constraints ->

        val imagePlaceable = imageMeasurables.first().measure(constraints)
        val usernamePlaceable = usernameMeasurables.first().measure(constraints)
        val datePlaceable = dateMeasurables.first().measure(constraints)
        val moreIconPlaceable = moreIconMeasurables.first().measure(constraints)

        val commentTextConstraints = constraints.copy(
            maxWidth = constraints.maxWidth - (24.dp.roundToPx() + imagePlaceable.width)
        )
        val commentTextPlaceable = commentTextMeasurables.first().measure(commentTextConstraints)

        val totalHeight = maxOf(
            imagePlaceable.height,
            (usernamePlaceable.height + commentTextPlaceable.height)
        )

        layout(width = constraints.maxWidth, height = totalHeight) {
            imagePlaceable.placeRelative(x = 0, y = 0)
            usernamePlaceable.placeRelative(
                x = imagePlaceable.width + (8.dp.roundToPx()),
                y = 0
            )
            datePlaceable.placeRelative(
                x = imagePlaceable.width + usernamePlaceable.width + (16.dp.roundToPx()),
                y = 3.dp.roundToPx()
            )

            moreIconPlaceable.placeRelative(
                x = (constraints.maxWidth) - (moreIconPlaceable.width),
                y = 0
            )

            commentTextPlaceable.place(
                x = imagePlaceable.width + (8.dp.roundToPx()),
                y = usernamePlaceable.height + (2.dp.roundToPx())
            )
        }
    }
}