package ru.ircover.schultetables.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableSettings
import java.util.*

@ExperimentalCoroutinesApi
class SchulteTableScoresRepositoryImplTest {
    private lateinit var scoresDao: ScoresDao
    private lateinit var scoreMapper: ScoreMapper
    private lateinit var sut: SchulteTableScoresRepositoryImpl

    @Before
    fun setup() {
        scoresDao = mock()
        scoreMapper = mock()
        sut = SchulteTableScoresRepositoryImpl(scoresDao, scoreMapper)
    }

    @Test
    fun add() = runBlockingTest {
        val value = SchulteTableScore(0L, SchulteTableSettings(1, 1),
            Calendar.getInstance())
        val mappedValue = Score(0, 0L, "", 0L)
        var changeEmitted = false
        `when`(scoreMapper.map(value)).thenReturn(mappedValue)
        launch {
            sut.getChanges().first()
            changeEmitted = true
        }

        sut.add(value)

        verify(scoresDao).insert(mappedValue)
        assertTrue("Не было вызвано событие об изменении", changeEmitted)
    }

    @Test
    fun getTop10_LessThen10() = runBlockingTest {
        val settings = SchulteTableSettings(0, 0)
        val mappedSettings = "test"
        val scores = listOf(
            Score(1, 1L, "", 1L),
            Score(2, 2L, "", 1L),
            Score(3, 3L, "", 1L),
            Score(4, 4L, "", 1L),
            Score(5, 5L, "", 1L),
            Score(6, 6L, "", 1L)
        )
        val date = Calendar.getInstance()
        val expectedResult = listOf(
            SchulteTableScore(1L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(2L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(3L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(4L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(5L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(6L, SchulteTableSettings(1, 1), date)
        )
        `when`(scoreMapper.mapSettings(settings)).thenReturn(mappedSettings)
        `when`(scoreMapper.map(any<Score>())).then {
            val score = it.arguments[0] as Score
            SchulteTableScore(score.duration, SchulteTableSettings(1, 1), date)
        }
        `when`(scoresDao.findBySettings(mappedSettings)).thenReturn(scores)

        val result = sut.getTop10(settings)

        assertEquals(expectedResult, result)
    }

    @Test
    fun getTop10_MoreThen10() = runBlockingTest {
        val settings = SchulteTableSettings(0, 0)
        val mappedSettings = "test"
        val scores = listOf(
            Score(1, 1L, "", 1L),
            Score(2, 2L, "", 1L),
            Score(3, 3L, "", 1L),
            Score(4, 4L, "", 1L),
            Score(5, 5L, "", 1L),
            Score(6, 6L, "", 1L),
            Score(7, 7L, "", 1L),
            Score(8, 8L, "", 1L),
            Score(9, 9L, "", 1L),
            Score(10, 10L, "", 1L),
            Score(11, 11L, "", 1L)
        )
        val date = Calendar.getInstance()
        val expectedResult = listOf(
            SchulteTableScore(1L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(2L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(3L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(4L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(5L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(6L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(7L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(8L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(9L, SchulteTableSettings(1, 1), date),
            SchulteTableScore(10L, SchulteTableSettings(1, 1), date)
        )
        `when`(scoreMapper.mapSettings(settings)).thenReturn(mappedSettings)
        `when`(scoreMapper.map(any<Score>())).then {
            val score = it.arguments[0] as Score
            SchulteTableScore(score.duration, SchulteTableSettings(1, 1), date)
        }
        `when`(scoresDao.findBySettings(mappedSettings)).thenReturn(scores)

        val result = sut.getTop10(settings)

        assertEquals(expectedResult, result)
    }
}