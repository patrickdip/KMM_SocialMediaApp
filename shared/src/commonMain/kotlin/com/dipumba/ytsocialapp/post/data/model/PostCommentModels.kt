package com.dipumba.ytsocialapp.post.data.model

import com.dipumba.ytsocialapp.common.util.DateFormatter
import com.dipumba.ytsocialapp.post.domain.model.PostComment
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal data class RemotePostComment(
    val commentId: Long,
    val content: String,
    val postId: Long,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val createdAt: String
){
    fun toDomainPostComment(isOwner: Boolean): PostComment{
        return PostComment(
            commentId = commentId,
            content = content,
            postId = postId,
            userId = userId,
            userName = userName,
            userImageUrl = userImageUrl,
            isOwner = isOwner,
            createdAt = DateFormatter.parseDate(createdAt)
        )
    }
}

@Serializable
internal data class GetPostCommentsResponseData(
    val success: Boolean,
    val comments: List<RemotePostComment> = listOf(),
    val message: String? = null
)

internal data class GetPostCommentsApiResponse(
    val code: HttpStatusCode,
    val data: GetPostCommentsResponseData
)

@Serializable
internal data class CommentResponseData(
    val success: Boolean,
    val comment: RemotePostComment? = null,
    val message: String? = null
)

internal data class CommentApiResponse(
    val code: HttpStatusCode,
    val data: CommentResponseData
)

@Serializable
internal data class NewCommentParams(
    val content: String,
    val postId: Long,
    val userId: Long
)

@Serializable
internal data class RemoveCommentParams(
    val postId: Long,
    val commentId: Long,
    val userId: Long
)




















