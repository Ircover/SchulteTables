package ru.ircover.schultetables.domain.usecase

import ru.ircover.schultetables.TimeManager
import ru.ircover.schultetables.dateFromMillis
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableScoresRepository
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker

interface SaveResultUseCase {
    suspend fun execute(startTimeMillis: Long)
}

class SaveResultUseCaseImpl(private val timeManager: TimeManager,
                            private val repository: SchulteTableScoresRepository,
                            private val settingsWorker: SchulteTableSettingsWorker
) : SaveResultUseCase {
    override suspend fun execute(startTimeMillis: Long) {
        val nowMillis = timeManager.getNowMillis()
        val duration = nowMillis - startTimeMillis
        repository.add(SchulteTableScore(duration, settingsWorker.get(), dateFromMillis(nowMillis)))
    }
}