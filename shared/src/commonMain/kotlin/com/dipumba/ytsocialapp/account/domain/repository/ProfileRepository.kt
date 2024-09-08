package com.dipumba.ytsocialapp.account.domain.repository

import com.dipumba.ytsocialapp.account.domain.model.Profile
import com.dipumba.ytsocialapp.common.util.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(profileId: Long): Flow<Result<Profile>>
    suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?): Result<Profile>
}