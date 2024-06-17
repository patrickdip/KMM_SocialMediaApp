package com.dipumba.ytsocialapp.common.data.remote

import com.dipumba.ytsocialapp.common.data.model.LikeApiResponse
import com.dipumba.ytsocialapp.common.data.model.LikeParams
import com.dipumba.ytsocialapp.common.data.model.PostsApiResponse
import com.dipumba.ytsocialapp.common.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

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

    suspend fun unlikePost(
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
}