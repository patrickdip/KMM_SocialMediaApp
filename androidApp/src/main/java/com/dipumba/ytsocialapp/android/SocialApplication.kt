package com.dipumba.ytsocialapp.android

import android.app.Application
import com.dipumba.ytsocialapp.android.di.appModule
import com.dipumba.ytsocialapp.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SocialApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SocialApplication)
            modules(appModule + getSharedModules())
        }
    }
}