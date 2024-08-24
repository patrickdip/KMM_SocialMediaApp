package com.dipumba.ytsocialapp.post.domain.repository

import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.domain.model.PostComment

internal interface PostCommentsRepository {
    suspend fun getPostComments(postId: Long, page: Int, pageSize: Int): Result<List<PostComment>>
    suspend fun addComment(postId: Long, content: String): Result<PostComment>
    suspend fun removeComment(postId: Long, commentId: Long): Result<PostComment?>
}