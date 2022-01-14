package ru.ircover.schultetables.app

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.ircover.schultetables.util.di.PresentationComponent
import ru.ircover.schultetables.util.di.PresentationComponentProvider
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, PresentationModule::class])
interface ApplicationComponent {
    fun getPresentationComponentFactory(): PresentationComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}

class SchulteTableApp: Application(), PresentationComponentProvider {
    private val applicationComponent = DaggerApplicationComponent.factory()
        .create(this)

    override fun get(): PresentationComponent = applicationComponent.getPresentationComponentFactory().create()
}