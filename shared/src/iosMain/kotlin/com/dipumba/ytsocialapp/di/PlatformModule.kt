package com.dipumba.ytsocialapp.di

import com.dipumba.ytsocialapp.common.data.IOSUserPreferences
import com.dipumba.ytsocialapp.common.data.createDatastore
import com.dipumba.ytsocialapp.common.data.local.UserPreferences
import org.koin.dsl.module

actual val platformModule = module {
    single<UserPreferences> { IOSUserPreferences(get()) }

    single {
        createDatastore()
    }
}