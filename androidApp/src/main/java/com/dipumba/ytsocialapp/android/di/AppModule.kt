package com.dipumba.ytsocialapp.android.di

import com.dipumba.ytsocialapp.android.auth.login.LoginViewModel
import com.dipumba.ytsocialapp.android.auth.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}