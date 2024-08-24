package com.dipumba.ytsocialapp.post.domain.usecase

import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.domain.model.PostComment
import com.dipumba.ytsocialapp.post.domain.repository.PostCommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemovePostCommentUseCase: KoinComponent {
    private val repository: PostCommentsRepository by inject()

    suspend operator fun invoke(postId: Long, commentId: Long): Result<PostComment?> {
        return repository.removeComment(postId = postId, commentId = commentId)
    }
}