package ru.ircover.schultetables.util.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule(private val application: Application) {

    //@Singleton
    @Provides
    fun provideContext(): Context {
        return application.applicationContext
    }

    //@Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}