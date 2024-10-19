package com.dipumba.ytsocialapp.android.di

import com.dipumba.ytsocialapp.android.MainActivityViewModel
import com.dipumba.ytsocialapp.android.account.edit.EditProfileViewModel
import com.dipumba.ytsocialapp.android.account.follows.FollowsViewModel
import com.dipumba.ytsocialapp.android.account.profile.ProfileViewModel
import com.dipumba.ytsocialapp.android.auth.login.LoginViewModel
import com.dipumba.ytsocialapp.android.auth.signup.SignUpViewModel
import com.dipumba.ytsocialapp.android.common.util.ImageBytesReader
import com.dipumba.ytsocialapp.android.home.HomeScreenViewModel
import com.dipumba.ytsocialapp.android.post.PostDetailViewModel
import com.dipumba.ytsocialapp.android.post.create_post.CreatePostViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { MainActivityViewModel(get())}
    viewModel { HomeScreenViewModel(get(), get(), get(), get())}
    viewModel { PostDetailViewModel(get(), get(), get(), get(), get())}
    viewModel { ProfileViewModel(get(), get(), get(), get()) }
    viewModel { EditProfileViewModel(get(), get(), get()) }
    viewModel { FollowsViewModel(get()) }
    single { ImageBytesReader(androidContext()) }
    viewModel { CreatePostViewModel(get(), get()) }
}