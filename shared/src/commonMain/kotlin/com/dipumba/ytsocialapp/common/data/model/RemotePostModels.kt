package com.dipumba.ytsocialapp.common.data.model

import com.dipumba.ytsocialapp.common.domain.model.Post
import com.dipumba.ytsocialapp.common.util.DateFormatter
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal data class RemotePost(
    val postId: Long,
    val caption: String,
    val imageUrl: String,
    val createdAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val userId: Long,
    val userName: String,
    val userImageUrl: String?,
    val isLiked: Boolean,
    val isOwnPost: Boolean
){
    fun toDomainPost(): Post {
        return Post(
            postId = postId,
            caption = caption,
            imageUrl = imageUrl,
            createdAt = DateFormatter.parseDate(createdAt),
            likesCount = likesCount,
            commentsCount = commentsCount,
            userId = userId,
            userName = userName,
            userImageUrl = userImageUrl,
            isLiked = isLiked,
            isOwnPost = isOwnPost
        )
    }
}


@Serializable
internal data class PostsApiResponseData(
    val success: Boolean,
    val posts: List<RemotePost> = listOf(),
    val message: String? = null
)

internal data class PostsApiResponse(
    val code: HttpStatusCode,
    val data: PostsApiResponseData
)