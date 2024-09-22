package com.dipumba.ytsocialapp.account.data

import com.dipumba.ytsocialapp.account.data.model.ProfileApiResponse
import com.dipumba.ytsocialapp.common.data.remote.KtorApi
import com.dipumba.ytsocialapp.common.util.Constants
import io.ktor.client.call.body
import io.ktor.client.request.forms.append
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

internal class AccountApiService : KtorApi() {
    suspend fun getProfile(
        token: String,
        profileId: Long,
        currentUserId: Long
    ): ProfileApiResponse {
        val httpResponse = client.get {
            endPoint(path = "/profile/$profileId")
            parameter(key = Constants.CURRENT_USER_ID_PARAMETER, value = currentUserId)
            setToken(token = token)
        }

        return ProfileApiResponse(code = httpResponse.status, data = httpResponse.body())
    }

    suspend fun updateProfile(
        token: String,
        profileData: String,
        imageBytes: ByteArray?
    ): ProfileApiResponse {
        val httpResponse = client.submitFormWithBinaryData(
            formData = formData {
                append(key = "profile_data", value = profileData)
                imageBytes?.let {
                    append(
                        key = "profile_image",
                        value = it,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, value = "image/*")
                            append(HttpHeaders.ContentDisposition, value = "filename=profile.jpg")
                        }
                    )
                }
            }
        ){
            endPoint(path = "/profile/update")
            setToken(token = token)
            setupMultipartRequest()
            method = HttpMethod.Post
        }
        return ProfileApiResponse(code = httpResponse.status, data = httpResponse.body())
    }
}















