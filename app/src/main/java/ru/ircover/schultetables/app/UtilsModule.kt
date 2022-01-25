package ru.ircover.schultetables.app

import dagger.Binds
import dagger.Module
import ru.ircover.schultetables.TimeManager
import ru.ircover.schultetables.TimeManagerImpl
import javax.inject.Singleton

@Module
interface UtilsModule {
    @Singleton
    @Binds
    fun bindTimeManager(timeManager: TimeManagerImpl): TimeManager
}