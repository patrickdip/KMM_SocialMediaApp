package com.dipumba.ytsocialapp.android.common.dummy_data

import com.dipumba.ytsocialapp.common.domain.model.FollowsUser

data class SampleFollowsUser(
    val id: Int,
    val name: String,
    val bio: String = "Hey there, welcome to my social app page!",
    val profileUrl: String,
    val isFollowing: Boolean = false
){
    fun toFollowsUser(): FollowsUser{
        return FollowsUser(
            id = id.toLong(),
            name = name,
            bio = bio,
            imageUrl = profileUrl,
            isFollowing = isFollowing
        )
    }
}

val sampleUsers = listOf(
    SampleFollowsUser(
        id = 1,
        name = "Mr Dip",
        profileUrl = "https://picsum.photos/200"
    ),
    SampleFollowsUser(
        id = 2,
        name = "John Cena",
        profileUrl = "https://picsum.photos/200"
    ),
    SampleFollowsUser(
        id = 3,
        name = "Cristiano",
        profileUrl = "https://picsum.photos/200"
    ),
    SampleFollowsUser(
        id = 4,
        name = "L. James",
        profileUrl = "https://picsum.photos/200"
    )
)