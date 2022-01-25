package ru.ircover.schultetables.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val duration: Long,
    val settings: String,
    @ColumnInfo(name = "date_millis") val dateMillis: Long)