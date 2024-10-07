package com.dipumba.ytsocialapp.common.data.remote

import com.dipumba.ytsocialapp.common.data.model.LikeApiResponse
import com.dipumba.ytsocialapp.common.data.model.LikeParams
import com.dipumba.ytsocialapp.common.data.model.PostApiResponse
import com.dipumba.ytsocialapp.common.data.model.PostsApiResponse
import com.dipumba.ytsocialapp.common.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.append
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

internal class PostApiService : KtorApi() {
    suspend fun getFeedPosts(
        userToken: String,
        currentUserId: Long,
        page: Int,
        pageSize: Int
    ): PostsApiResponse {
        val httpResponse = client.get {
            endPoint(path = "/posts/feed")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            parameter(key = Constants.PAGE_QUERY_PARAMETER, value = page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, value = pageSize)
            setToken(token = userToken)
        }
        return PostsApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun likePost(
        userToken: String,
        likeParams: LikeParams
    ): LikeApiResponse {
        val httpResponse = client.post {
            endPoint(path = "/post/likes/add")
            setBody(likeParams)
            setToken(token = userToken)
        }
        return LikeApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun dislikePost(
        userToken: String,
        likeParams: LikeParams
    ): LikeApiResponse {
        val httpResponse = client.delete {
            endPoint(path = "/post/likes/remove")
            setBody(likeParams)
            setToken(token = userToken)
        }
        return LikeApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun getUserPosts(
        token: String,
        userId: Long,
        currentUserId: Long,
        page: Int,
        pageSize: Int
    ): PostsApiResponse {
        val httpResponse = client.get {
            endPoint(path = "/posts/$userId")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            parameter(key = Constants.PAGE_QUERY_PARAMETER, value = page)
            parameter(key = Constants.PAGE_SIZE_QUERY_PARAMETER, value = pageSize)
            setToken(token = token)
        }
        return PostsApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun getPost(
        token: String,
        postId: Long,
        currentUserId: Long
    ): PostApiResponse {
        val httpResponse = client.get {
            endPoint(path = "/post/$postId")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            setToken(token = token)
        }
        return PostApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun createPost(
        token: String,
        newPostData: String,
        imageBytes: ByteArray
    ): PostApiResponse{
        val httpResponse = client.submitFormWithBinaryData(
            formData = formData {
                append(key = "post_data", value = newPostData)
                append(
                    key = "post_image",
                    value = imageBytes,
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, value = "image/*")
                        append(HttpHeaders.ContentDisposition, value = "filename=post.jpg")
                    }
                )
            }
        ){
            endPoint(path = "/post/create")
            setToken(token = token)
            setupMultipartRequest()
            method = HttpMethod.Post
        }
        return PostApiResponse(code = httpResponse.status, data = httpResponse.body())
    }
}








