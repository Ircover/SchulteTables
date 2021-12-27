package ru.ircover.schultetables.domain.usecase

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.ircover.schultetables.domain.SchulteTableCell
import ru.ircover.schultetables.domain.SchulteTableSettings

class GenerateCellsOrderedListUseCaseImplTest {
    private lateinit var sut: GenerateCellsOrderedListUseCaseImpl

    @Before
    fun setup() {
        sut =  GenerateCellsOrderedListUseCaseImpl()
    }

    @Test
    fun execute_numbers() {
        val settings = SchulteTableSettings(3, 2)
        val expectedResult = listOf(getCell(1), getCell(2), getCell(3),
            getCell(4), getCell(5), getCell(6))

        val result = sut.execute(settings)

        assertEquals(expectedResult, result)
    }

    private fun getCell(num: Int) = SchulteTableCell(num.toString(), num)
}