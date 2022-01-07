package ru.ircover.schultetables.util.di

import dagger.Subcomponent
import ru.ircover.schultetables.ActivityScope
import ru.ircover.schultetables.view.fragment.GameFragment
import ru.ircover.schultetables.view.fragment.SettingsFragment

//@ActivityScope
@Subcomponent(modules = [DomainModule::class, PresentationModule::class])
interface PresentationComponent {
    fun inject(gameFragment: GameFragment)
    fun inject(settingsFragment: SettingsFragment)

    /*@Subcomponent.Builder
    interface Builder {
        fun setPresentationModule(presentationModule: PresentationModule)
        fun setDomainModule(domainModule: DomainModule)
        fun build(): PresentationComponent
    }*/
    @Subcomponent.Factory
    interface Factory {
        fun create(presentationModule: PresentationModule): PresentationComponent
    }
}

interface PresentationComponentProvider {
    fun get(): PresentationComponent
}