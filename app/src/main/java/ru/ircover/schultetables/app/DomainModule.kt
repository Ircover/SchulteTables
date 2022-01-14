package ru.ircover.schultetables.app

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.ircover.schultetables.domain.*
import ru.ircover.schultetables.domain.usecase.*
import ru.ircover.schultetables.util.DispatchersProvider
import ru.ircover.schultetables.util.DispatchersProviderImpl
import ru.ircover.schultetables.util.SchulteTableSettingsWorkerImpl
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    fun provideSchulteTableGame(): SchulteTableGame = SchulteTableGameImpl()

    @Singleton
    @Provides
    fun provideSchulteTableSettingsWorker(context: Context, gson: Gson): SchulteTableSettingsWorker =
        SchulteTableSettingsWorkerImpl(context, gson)

    @Provides
    fun provideGenerateTableUseCase(settingsWorker: SchulteTableSettingsWorker,
                                    generateCellsOrderedListUseCase: GenerateCellsOrderedListUseCase): GenerateTableUseCase =
        GenerateTableUseCaseImpl(settingsWorker, generateCellsOrderedListUseCase)

    @Provides
    fun provideClickCellUseCase(): ClickCellUseCase =
            ClickCellUseCaseImpl()

    @Provides
    fun provideGenerateCellsListUseCase(): GenerateCellsOrderedListUseCase =
            GenerateCellsOrderedListUseCaseImpl()

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider =
        DispatchersProviderImpl()
}