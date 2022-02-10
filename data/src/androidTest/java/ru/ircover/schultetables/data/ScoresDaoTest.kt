package ru.ircover.schultetables.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScoresDaoTest {
    private lateinit var scoresDao: ScoresDao
    private lateinit var db: AppDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDataBase::class.java).build()
        scoresDao = db.scoresDao()
    }

    @Test
    fun insertAndRead() = runBlocking {
        val value1 = Score(1, 5, "test1", 10)
        val value2 = Score(2, 4, "test1", 15)
        val value3 = Score(3, 3, "test2", 20)

        listOf(value1, value2, value3).forEach {
            scoresDao.insert(it)
        }
        val result1 = scoresDao.findBySettings("test1")
        val result2 = scoresDao.findBySettings("test2")

        assertEquals("", listOf(value2, value1), result1)
        assertEquals("", listOf(value3), result2)
    }

    @After
    fun closeDb() {
        db.close()
    }
}