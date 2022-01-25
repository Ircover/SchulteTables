package ru.ircover.schultetables.util.di

import dagger.Subcomponent
import ru.ircover.schultetables.ActivityScope
import ru.ircover.schultetables.main.view.GameFragment
import ru.ircover.schultetables.main.view.ScoresFragment
import ru.ircover.schultetables.main.view.SettingsFragment

@ActivityScope
@Subcomponent
interface PresentationComponent {
    fun inject(gameFragment: GameFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(scoresFragment: ScoresFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): PresentationComponent
    }
}

interface PresentationComponentProvider {
    fun get(): PresentationComponent
}