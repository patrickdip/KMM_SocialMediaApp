package com.dipumba.ytsocialapp.post.domain.usecase

import com.dipumba.ytsocialapp.common.domain.model.Post
import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LikeOrDislikePostUseCase : KoinComponent {
    private val repository by inject<PostRepository>()

    suspend operator fun invoke(post: Post): Result<Boolean> {
        return repository.likeOrDislikePost(post.postId, !post.isLiked)
    }
}