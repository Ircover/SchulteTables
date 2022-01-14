package ru.ircover.schultetables.app

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule() {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}