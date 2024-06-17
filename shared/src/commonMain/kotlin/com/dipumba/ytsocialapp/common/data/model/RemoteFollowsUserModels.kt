package com.dipumba.ytsocialapp.common.data.model

import com.dipumba.ytsocialapp.common.domain.model.FollowsUser
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
internal data class FollowsParams(
    val follower: Long,
    val following: Long
)

@Serializable
internal data class RemoteFollowUser(
    val id: Long,
    val name: String,
    val bio: String,
    val imageUrl: String? = null,
    val isFollowing: Boolean
){
    fun toDomainFollowUser(): FollowsUser {
        return FollowsUser(id, name, bio, imageUrl, isFollowing)
    }
}

@Serializable
internal data class FollowsApiResponseData(
    val success: Boolean,
    val follows: List<RemoteFollowUser> = listOf(),
    val message: String? = null
)

internal data class FollowsApiResponse(
    val code: HttpStatusCode,
    val data: FollowsApiResponseData
)

@Serializable
internal data class FollowOrUnfollowResponseData(
    val success: Boolean,
    val message: String? = null
)

internal data class FollowOrUnfollowApiResponse(
    val code: HttpStatusCode,
    val data: FollowOrUnfollowResponseData
)











