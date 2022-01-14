package ru.ircover.schultetables.util.di

import dagger.Subcomponent
import ru.ircover.schultetables.ActivityScope
import ru.ircover.schultetables.view.fragment.GameFragment
import ru.ircover.schultetables.view.fragment.SettingsFragment

@ActivityScope
@Subcomponent
interface PresentationComponent {
    fun inject(gameFragment: GameFragment)
    fun inject(settingsFragment: SettingsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): PresentationComponent
    }
}

interface PresentationComponentProvider {
    fun get(): PresentationComponent
}