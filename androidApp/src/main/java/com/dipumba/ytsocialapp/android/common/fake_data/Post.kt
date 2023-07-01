package com.dipumba.ytsocialapp.android.common.fake_data

data class Post(
    val id: String,
    val text: String,
    val imageUrl: String,
    val createdAt: String,
    val likesCount: Int,
    val commentCount: Int,
    val authorId: Int,
    val authorName: String,
    val authorImage: String,
    val isLiked: Boolean = false,
    val isOwnPost: Boolean = false
)


val samplePosts = listOf(
    Post(
        id = "11",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "20 min",
        likesCount = 12,
        commentCount = 3,
        authorId = 1,
        authorName = "Mr Dip",
        authorImage = "https://picsum.photos/200"
    ),
    Post(
        id = "12",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "May 03, 2023",
        likesCount = 121,
        commentCount = 23,
        authorId = 2,
        authorName = "John Cena",
        authorImage = "https://picsum.photos/200"
    ),
    Post(
        id = "13",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "Apr 12, 2023",
        likesCount = 221,
        commentCount = 41,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200"
    ),
    Post(
        id = "14",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "Mar 31, 2023",
        likesCount = 90,
        commentCount = 13,
        authorId = 3,
        authorName = "Cristiano",
        authorImage = "https://picsum.photos/200"
    ),
    Post(
        id = "15",
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        imageUrl = "https://picsum.photos/400",
        createdAt = "Jan 30, 2023",
        likesCount = 121,
        commentCount = 31,
        authorId = 4,
        authorName = "L. James",
        authorImage = "https://picsum.photos/200"
    ),
)