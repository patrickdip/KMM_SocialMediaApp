package com.dipumba.ytsocialapp.post.domain

import com.dipumba.ytsocialapp.common.domain.model.Post
import com.dipumba.ytsocialapp.common.util.Result

interface PostRepository {
    suspend fun getFeedPosts(page: Int, pageSize: Int): Result<List<Post>>
    suspend fun likeOrUnlikePost(postId: Long, shouldLike: Boolean): Result<Boolean>
}