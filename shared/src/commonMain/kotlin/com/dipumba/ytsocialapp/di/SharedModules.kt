package com.dipumba.ytsocialapp.di

import com.dipumba.ytsocialapp.account.data.AccountApiService
import com.dipumba.ytsocialapp.account.data.repository.ProfileRepositoryImpl
import com.dipumba.ytsocialapp.account.domain.repository.ProfileRepository
import com.dipumba.ytsocialapp.account.domain.usecase.GetProfileUseCase
import com.dipumba.ytsocialapp.account.domain.usecase.UpdateProfileUseCase
import com.dipumba.ytsocialapp.auth.data.AuthRepositoryImpl
import com.dipumba.ytsocialapp.auth.data.AuthService
import com.dipumba.ytsocialapp.auth.domain.repository.AuthRepository
import com.dipumba.ytsocialapp.auth.domain.usecase.SignInUseCase
import com.dipumba.ytsocialapp.auth.domain.usecase.SignUpUseCase
import com.dipumba.ytsocialapp.common.data.remote.FollowsApiService
import com.dipumba.ytsocialapp.common.data.remote.PostApiService
import com.dipumba.ytsocialapp.common.util.provideDispatcher
import com.dipumba.ytsocialapp.follows.data.FollowsRepositoryImpl
import com.dipumba.ytsocialapp.follows.domain.FollowsRepository
import com.dipumba.ytsocialapp.follows.domain.usecase.FollowOrUnfollowUseCase
import com.dipumba.ytsocialapp.follows.domain.usecase.GetFollowableUsersUseCase
import com.dipumba.ytsocialapp.follows.domain.usecase.GetFollowsUseCase
import com.dipumba.ytsocialapp.post.data.PostRepositoryImpl
import com.dipumba.ytsocialapp.post.data.remote.PostCommentsApiService
import com.dipumba.ytsocialapp.post.data.repository.PostCommentsRepositoryImpl
import com.dipumba.ytsocialapp.post.domain.repository.PostCommentsRepository
import com.dipumba.ytsocialapp.post.domain.repository.PostRepository
import com.dipumba.ytsocialapp.post.domain.usecase.AddPostCommentUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.GetPostCommentsUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.GetPostUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.GetPostsUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.GetUserPostsUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.LikeOrDislikePostUseCase
import com.dipumba.ytsocialapp.post.domain.usecase.RemovePostCommentUseCase
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

private val postModule = module {
    factory { PostApiService() }
    factory { GetPostsUseCase() }
    factory { LikeOrDislikePostUseCase() }
    factory { GetUserPostsUseCase() }
    factory { GetPostUseCase() }

    single<PostRepository> { PostRepositoryImpl(get(), get(), get()) }
}

private val postCommentModule = module {
    factory { PostCommentsApiService() }
    factory { GetPostCommentsUseCase() }
    factory { AddPostCommentUseCase() }
    factory { RemovePostCommentUseCase() }

    single<PostCommentsRepository> { PostCommentsRepositoryImpl(get(), get(), get()) }
}

private val followsModule = module {
    factory { FollowsApiService() }
    factory { GetFollowableUsersUseCase() }
    factory { FollowOrUnfollowUseCase() }
    factory { GetFollowsUseCase() }

    single<FollowsRepository> { FollowsRepositoryImpl(get(), get(), get()) }
}

private val accountModule = module {
    factory { AccountApiService() }
    factory { GetProfileUseCase() }
    factory { UpdateProfileUseCase() }
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
}

fun getSharedModules() = listOf(
    platformModule,
    authModule,
    utilityModule,
    postModule,
    followsModule,
    accountModule,
    postCommentModule
)