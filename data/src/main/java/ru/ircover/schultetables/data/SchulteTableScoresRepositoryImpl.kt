package ru.ircover.schultetables.data

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableScoresRepository
import ru.ircover.schultetables.domain.SchulteTableSettings

class SchulteTableScoresRepositoryImpl(private val scoresDao: ScoresDao,
                                       private val mapper: ScoreMapper) : SchulteTableScoresRepository {
    private val changes: MutableSharedFlow<Unit> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun add(score: SchulteTableScore) {
        scoresDao.insert(mapper.map(score))
        changes.emit(Unit)
    }

    override suspend fun getTop10(settings: SchulteTableSettings): List<SchulteTableScore> =
        scoresDao.findBySettings(mapper.mapSettings(settings))
            .take(10)
            .map { mapper.map(it) }

    override fun getChanges(): Flow<Unit> = changes
}