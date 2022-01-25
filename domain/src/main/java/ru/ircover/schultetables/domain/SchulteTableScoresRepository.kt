package ru.ircover.schultetables.domain

import kotlinx.coroutines.flow.Flow

interface SchulteTableScoresRepository {
    suspend fun add(score: SchulteTableScore)
    suspend fun getTop10(settings: SchulteTableSettings): List<SchulteTableScore>
    fun getChanges(): Flow<Unit>
}