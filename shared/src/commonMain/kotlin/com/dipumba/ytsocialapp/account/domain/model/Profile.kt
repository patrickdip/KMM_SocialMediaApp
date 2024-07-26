package com.dipumba.ytsocialapp.account.domain.model

data class Profile(
    val id: Long,
    val name: String,
    val bio: String,
    val imageUrl: String?,
    val followersCount: Int,
    val followingCount: Int,
    val isFollowing: Boolean,
    val isOwnProfile: Boolean
)