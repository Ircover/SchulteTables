package ru.ircover.schultetables.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoresDao {
    @Insert
    suspend fun insert(score: Score)

    @Query("SELECT * FROM score WHERE settings = :settings ORDER BY duration")
    suspend fun findBySettings(settings: String): List<Score>
}