package com.dipumba.ytsocialapp.android.common.datastore

import com.dipumba.ytsocialapp.auth.domain.model.AuthResultData
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val id: Int = 0,
    val name: String = "",
    val bio: String = "",
    val avatar: String? = null,
    val token: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0
)

fun AuthResultData.toUserPreferences(): UserPreferences{
    return UserPreferences(
        id, name, bio, avatar, token, followersCount, followingCount
    )
}

fun UserPreferences.toAuthResultData(): AuthResultData{
    return AuthResultData(
        id, name, bio, avatar, token, followersCount, followingCount
    )
}
