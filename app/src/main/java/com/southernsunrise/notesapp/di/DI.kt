package com.southernsunrise.notesapp.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object DI {

    private lateinit var diApp: KoinApplication

    fun init(appContext: Context) {
        diApp = startKoin {
            androidContext(appContext)
            modules(dataModule, presentationModule)
        }
    }
}
