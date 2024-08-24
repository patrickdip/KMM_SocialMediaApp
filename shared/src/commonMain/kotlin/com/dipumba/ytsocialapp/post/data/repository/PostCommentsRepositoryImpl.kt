package com.dipumba.ytsocialapp.post.data.repository

import com.dipumba.ytsocialapp.common.data.local.UserPreferences
import com.dipumba.ytsocialapp.common.util.Constants
import com.dipumba.ytsocialapp.common.util.DispatcherProvider
import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.post.data.model.NewCommentParams
import com.dipumba.ytsocialapp.post.data.model.RemoveCommentParams
import com.dipumba.ytsocialapp.post.data.remote.PostCommentsApiService
import com.dipumba.ytsocialapp.post.domain.model.PostComment
import com.dipumba.ytsocialapp.post.domain.repository.PostCommentsRepository
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.withContext

internal class PostCommentsRepositoryImpl(
    private val preferences: UserPreferences,
    private val postCommentsApiService: PostCommentsApiService,
    private val dispatcherProvider: DispatcherProvider
) : PostCommentsRepository {
    override suspend fun getPostComments(
        postId: Long,
        page: Int,
        pageSize: Int
    ): Result<List<PostComment>> {
        return withContext(dispatcherProvider.io){
            try {
                val currentUserData = preferences.getUserData()

                val apiResponse = postCommentsApiService.getPostComments(
                    userToken = currentUserData.token,
                    postId = postId,
                    page = page,
                    pageSize = pageSize
                )

                if (apiResponse.code == HttpStatusCode.OK){
                    Result.Success(data = apiResponse.data.comments.map { it.toDomainPostComment() })
                }else{
                    Result.Error(message = apiResponse.data.message ?: Constants.UNEXPECTED_ERROR)
                }
            }catch (ioException: IOException){
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            }catch (anyError: Throwable){
                Result.Error(message = Constants.UNEXPECTED_ERROR)
            }
        }
    }

    override suspend fun addComment(postId: Long, content: String): Result<PostComment> {
        return withContext(dispatcherProvider.io){
            try {
                val currentUserData = preferences.getUserData()
                val params = NewCommentParams(
                    content = content,
                    postId = postId,
                    userId = currentUserData.id
                )

                val apiResponse = postCommentsApiService.addComment(
                    commentParams = params,
                    userToken = currentUserData.token
                )

                if (apiResponse.code == HttpStatusCode.OK){
                    Result.Success(data = apiResponse.data.comment!!.toDomainPostComment())
                }else{
                    Result.Error(message = apiResponse.data.message ?: Constants.UNEXPECTED_ERROR)
                }
            }catch (ioError: IOException){
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            }catch (anyError: Throwable){
                Result.Error(message = Constants.UNEXPECTED_ERROR)
            }
        }
    }

    override suspend fun removeComment(postId: Long, commentId: Long): Result<PostComment?> {
        return withContext(dispatcherProvider.io){
            try {
                val currentUserData = preferences.getUserData()
                val params = RemoveCommentParams(
                    postId = postId,
                    commentId = commentId,
                    userId = currentUserData.id
                )

                val apiResponse = postCommentsApiService.removeComment(
                    commentParams = params,
                    userToken = currentUserData.token
                )

                if (apiResponse.code == HttpStatusCode.OK){
                    val comment = apiResponse.data.comment?.toDomainPostComment()
                    Result.Success(data = comment)
                }else{
                    Result.Error(message = apiResponse.data.message ?: Constants.UNEXPECTED_ERROR)
                }
            }catch (ioError: IOException){
                Result.Error(message = Constants.NO_INTERNET_ERROR)
            }catch (anyError: Throwable){
                Result.Error(message = Constants.UNEXPECTED_ERROR)
            }
        }
    }
}

















