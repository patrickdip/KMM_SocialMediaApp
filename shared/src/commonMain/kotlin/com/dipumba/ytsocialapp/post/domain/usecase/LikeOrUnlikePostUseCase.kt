package com.dipumba.ytsocialapp.post.domain.usecase

import com.dipumba.ytsocialapp.common.domain.model.Post
import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.domain.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LikeOrUnlikePostUseCase : KoinComponent {
    private val repository by inject<PostRepository>()

    suspend operator fun invoke(post: Post): Result<Boolean> {
        return repository.likeOrUnlikePost(post.postId, !post.isLiked)
    }
}