package com.dipumba.ytsocialapp.follows.domain.usecase

import com.dipumba.ytsocialapp.common.domain.model.FollowsUser
import com.dipumba.ytsocialapp.common.util.Result
import com.dipumba.ytsocialapp.follows.domain.FollowsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFollowableUsersUseCase: KoinComponent {
    private val repository by inject<FollowsRepository>()

    suspend operator fun invoke(): Result<List<FollowsUser>> {
        return repository.getFollowableUsers()
    }
}