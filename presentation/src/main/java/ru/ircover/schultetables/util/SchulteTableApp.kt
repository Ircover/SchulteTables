package ru.ircover.schultetables.util

import android.app.Application
import dagger.Component
import ru.ircover.schultetables.util.di.DomainModule
import ru.ircover.schultetables.util.di.PresentationModule
import ru.ircover.schultetables.view.fragment.GameFragment
import ru.ircover.schultetables.view.fragment.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, PresentationModule::class])
interface ApplicationComponent {
    fun inject(gameFragment: GameFragment)
    fun inject(settingsFragment: SettingsFragment)
}

class SchulteTableApp: Application() {
    val applicationComponent: ApplicationComponent = DaggerApplicationComponent.builder()
        .presentationModule(PresentationModule(this))
        .build()
}