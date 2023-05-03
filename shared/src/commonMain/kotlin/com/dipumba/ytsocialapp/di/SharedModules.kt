package com.dipumba.ytsocialapp.di

import com.dipumba.ytsocialapp.auth.data.AuthRepositoryImpl
import com.dipumba.ytsocialapp.auth.data.AuthService
import com.dipumba.ytsocialapp.auth.domain.repository.AuthRepository
import com.dipumba.ytsocialapp.auth.domain.usecase.SignInUseCase
import com.dipumba.ytsocialapp.auth.domain.usecase.SignUpUseCase
import com.dipumba.ytsocialapp.common.util.provideDispatcher
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}

fun getSharedModules() = listOf(authModule, utilityModule)