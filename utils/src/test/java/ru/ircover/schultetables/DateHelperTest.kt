package ru.ircover.schultetables

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.GregorianCalendar

class DateHelperTest {
    @Test
    fun dateFromMillis() {
        val millis = 1644655188000L
        val expectedDate = GregorianCalendar(2022, 1, 12, 11, 39, 48)

        val result = dateFromMillis(millis)

        assertEquals(expectedDate, result)
    }
}