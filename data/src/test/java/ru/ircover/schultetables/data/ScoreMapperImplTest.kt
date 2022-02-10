package ru.ircover.schultetables.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import ru.ircover.schultetables.Serializer
import ru.ircover.schultetables.domain.SchulteTableScore
import ru.ircover.schultetables.domain.SchulteTableSettings
import java.util.*

class ScoreMapperImplTest {
    private lateinit var sut: ScoreMapperImpl
    private lateinit var serializer: Serializer

    @Before
    fun setup() {
        serializer = mock()
        sut = ScoreMapperImpl(serializer)
    }

    @Test
    fun map_FromScore() {
        val settingsString = "test"
        val settings = SchulteTableSettings(1, 1)
        val value = Score(1, 2L, settingsString, 5L)
        `when`(serializer.deserialize(settingsString, SchulteTableSettings::class.java))
            .thenReturn(settings)

        val result = sut.map(value)

        assertEquals("Неверно задана продолжительность", 2L, result.duration)
        assertEquals("Неверно задана дата", 5L, result.dateTime.timeInMillis)
        assertEquals("Неверно заданы настройки", settings, result.settings)
    }

    @Test
    fun map_FromSchulteTableScore() {
        val settings = SchulteTableSettings(1, 1)
        val settingsString = "test"
        val date = Calendar.getInstance()
        val value = SchulteTableScore(1, settings, date)
        `when`(serializer.serialize(settings))
            .thenReturn(settingsString)

        val result = sut.map(value)

        assertEquals("Неверно задана продолжительность", 1L, result.duration)
        assertEquals("Неверно задана дата", date.timeInMillis, result.dateMillis)
        assertEquals("Неверно заданы настройки", settingsString, result.settings)
    }
}