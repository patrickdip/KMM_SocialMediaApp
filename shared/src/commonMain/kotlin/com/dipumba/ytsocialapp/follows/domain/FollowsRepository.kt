package com.dipumba.ytsocialapp.follows.domain

import com.dipumba.ytsocialapp.common.domain.model.FollowsUser
import com.dipumba.ytsocialapp.common.util.Result

interface FollowsRepository {
    suspend fun getFollowableUsers(): Result<List<FollowsUser>>
    suspend fun followOrUnfollow(followedUserId: Long, shouldFollow: Boolean): Result<Boolean>
}