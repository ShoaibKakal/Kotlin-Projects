package com.shoaib.imagefilterapp.utilities

import android.app.Application
import com.shoaib.imagefilterapp.dependencyinjection.repositoryViewModule
import com.shoaib.imagefilterapp.dependencyinjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppConfig : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryViewModule, viewModelModule))
        }
    }
}