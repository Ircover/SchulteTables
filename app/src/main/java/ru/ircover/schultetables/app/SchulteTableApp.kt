package ru.ircover.schultetables.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import ru.ircover.schultetables.util.di.DomainModule
import ru.ircover.schultetables.util.di.PresentationComponent
import ru.ircover.schultetables.util.di.PresentationComponentProvider
import ru.ircover.schultetables.util.di.PresentationModule
import javax.inject.Singleton

@Module(subcomponents = [PresentationComponent::class])
class SubcomponentsModule

@Singleton
@Component//(modules = [SubcomponentsModule::class, DomainModule::class, PresentationModule::class])
interface ApplicationComponent {
    fun getPresentationComponentFactory(): PresentationComponent.Factory

    /*@Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }*/

    @Component.Factory
    interface Factory {
        fun create(/*presentationModule: PresentationModule*/): ApplicationComponent
    }
}

class SchulteTableApp: Application(), PresentationComponentProvider {
    private val applicationComponent = DaggerApplicationComponent.builder()
        .application(this)
        .build()

    override fun get(): PresentationComponent = applicationComponent.getPresentationComponentFactory().create()
}