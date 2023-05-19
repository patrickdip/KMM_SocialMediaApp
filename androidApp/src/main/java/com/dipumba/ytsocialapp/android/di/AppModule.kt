package com.dipumba.ytsocialapp.android.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.dipumba.ytsocialapp.android.MainViewModel
import com.dipumba.ytsocialapp.android.auth.login.LoginViewModel
import com.dipumba.ytsocialapp.android.auth.signup.SignUpViewModel
import com.dipumba.ytsocialapp.android.common.datastore.UserPreferences
import com.dipumba.ytsocialapp.android.common.datastore.UserSettingsSerializer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { MainViewModel(get())}

    single {
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                androidContext().dataStoreFile(fileName = "social-app-prefs")
            }
        )
    }
}