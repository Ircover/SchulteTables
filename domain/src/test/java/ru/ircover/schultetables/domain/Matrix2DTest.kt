package ru.ircover.schultetables.domain

import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalArgumentException

class Matrix2DTest {
    @Test
    fun emptyMatrix() {
        val sut = Matrix2D<Int>(arrayOf())

        assertEquals("Неверно задана высота", 0, sut.height)
        assertEquals("Неверно задана ширина", 0, sut.width)
    }
    @Test
    fun commonMatrix() {
        val sut = Matrix2D(
            arrayOf(
                arrayOf(1, 2, 3),
                arrayOf(4, 5, 6)
            )
        )

        assertEquals("Неверно задана высота", 2, sut.height)
        assertEquals("Неверно задана ширина", 3, sut.width)
        assertEquals("Неверно получено значение по координатам", 6, sut.get(2, 1))
    }

    @Test
    fun notFlatMatrix_throws() {
        assertThrows(IllegalArgumentException::class.java) {
            Matrix2D(
                arrayOf(
                    arrayOf(1, 2, 3),
                    arrayOf(4, 5)
                )
            )
        }
    }

    @Test
    fun flatMap() {
        val sut = Matrix2D(
            arrayOf(
                arrayOf(1, 2, 3),
                arrayOf(4, 5, 6)
            )
        )
        val expectedResult = listOf(1, 2, 3, 4, 5, 6)

        val result = sut.flatMap()

        assertEquals(expectedResult, result)
    }
}