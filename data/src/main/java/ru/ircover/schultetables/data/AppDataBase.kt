package ru.ircover.schultetables.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Score::class], version = 2)
abstract class AppDataBase : RoomDatabase() {
    abstract fun scoresDao(): ScoresDao
}