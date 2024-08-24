package com.dipumba.ytsocialapp.post.domain.usecase

import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.domain.model.PostComment
import com.dipumba.ytsocialapp.post.domain.repository.PostCommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddPostCommentUseCase: KoinComponent {
    private val repository: PostCommentsRepository by inject()

    suspend operator fun invoke(postId: Long, content: String): Result<PostComment> {
        if (content.isBlank()){
            return Result.Error(message = "Comment content cannot be empty")
        }
        return repository.addComment(postId = postId, content = content)
    }
}