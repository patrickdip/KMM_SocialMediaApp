package com.dipumba.ytsocialapp.android.di

import com.dipumba.ytsocialapp.android.MainActivityViewModel
import com.dipumba.ytsocialapp.android.account.edit.EditProfileViewModel
import com.dipumba.ytsocialapp.android.account.follows.FollowsViewModel
import com.dipumba.ytsocialapp.android.account.profile.ProfileViewModel
import com.dipumba.ytsocialapp.android.auth.login.LoginViewModel
import com.dipumba.ytsocialapp.android.auth.signup.SignUpViewModel
import com.dipumba.ytsocialapp.android.home.HomeScreenViewModel
import com.dipumba.ytsocialapp.android.post.PostDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainActivityViewModel(get())}
    viewModel { HomeScreenViewModel(get(), get(), get(), get())}
    viewModel { PostDetailViewModel()}
    viewModel { ProfileViewModel() }
    viewModel { EditProfileViewModel() }
    viewModel { FollowsViewModel() }
}