package ru.ircover.schultetables.app

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.ircover.schultetables.Serializer
import ru.ircover.schultetables.TimeManager
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
    fun provideSchulteTableSettingsWorker(context: Context, serializer: Serializer): SchulteTableSettingsWorker =
        SchulteTableSettingsWorkerImpl(context, serializer)

    @Provides
    fun provideGenerateTableUseCase(settingsWorker: SchulteTableSettingsWorker,
                                    generateCellsOrderedListUseCase: GenerateCellsOrderedListUseCase,
                                    timeManager: TimeManager): GenerateTableUseCase =
        GenerateTableUseCaseImpl(settingsWorker, generateCellsOrderedListUseCase, timeManager)

    @Provides
    fun provideClickCellUseCase(): ClickCellUseCase =
            ClickCellUseCaseImpl()

    @Provides
    fun provideGenerateCellsListUseCase(): GenerateCellsOrderedListUseCase =
            GenerateCellsOrderedListUseCaseImpl()

    @Provides
    fun provideDispatchersProvider(): DispatchersProvider =
        DispatchersProviderImpl()

    @Provides
    fun provideSaveResultUseCase(timeManager: TimeManager,
                                 repository: SchulteTableScoresRepository,
                                 settingsWorker: SchulteTableSettingsWorker): SaveResultUseCase =
        SaveResultUseCaseImpl(timeManager, repository, settingsWorker)
}