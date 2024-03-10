package com.dipumba.ytsocialapp.auth.domain.model

data class AuthResultData(
    val id: Long,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val token: String,
    val followersCount: Int = 0,
    val followingCount: Int = 0
)