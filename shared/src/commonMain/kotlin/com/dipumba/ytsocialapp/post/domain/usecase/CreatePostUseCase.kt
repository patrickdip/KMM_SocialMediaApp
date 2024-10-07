package com.dipumba.ytsocialapp.post.domain.usecase

import com.dipumba.ytsocialapp.common.domain.model.Post
import com.dipumba.ytsocialapp.common.util.Constants
import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreatePostUseCase: KoinComponent {
    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        caption: String,
        imageBytes: ByteArray
    ): Result<Post>{
        with(caption){
            if (isBlank() || length > 200){
                return Result.Error(message = Constants.INVALID_INPUT_POST_CAPTION_MESSAGE)
            }
        }
        return repository.createPost(caption = caption, imageBytes = imageBytes)
    }
}