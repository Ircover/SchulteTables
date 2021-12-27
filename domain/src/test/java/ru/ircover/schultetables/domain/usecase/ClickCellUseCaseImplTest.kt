package ru.ircover.schultetables.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import ru.ircover.schultetables.domain.Matrix2D
import ru.ircover.schultetables.domain.SchulteTableCell
import ru.ircover.schultetables.domain.SchulteTableGame

@ExperimentalCoroutinesApi
class ClickCellUseCaseImplTest {
    private lateinit var sut: ClickCellUseCaseImpl

    @Before
    fun setup() {
        sut =  ClickCellUseCaseImpl()
    }

    @Test
    fun execute_wrongCell() = runBlockingTest {
        val game = mock<SchulteTableGame>()
        val clickCell = getCell(3)
        `when`(game.getCurrentExpectedCell()).thenReturn(getCell(4))

        val result = sut.execute(game, clickCell)

        assertFalse("Неверно посчитан флаг корректного клика", result)
        verify(game, never()).setExpectedCell(any())
        verify(game, never()).finish()
    }

    @Test
    fun execute_correctCell() = runBlockingTest {
        val game = mock<SchulteTableGame>()
        val clickCell = getCell(3)
        `when`(game.getCurrentExpectedCell()).thenReturn(getCell(3))
        `when`(game.getCurrentCells()).thenReturn(Matrix2D(arrayOf(
            arrayOf(getCell(1), getCell(2)),
            arrayOf(getCell(3), getCell(4))
        )))

        val result = sut.execute(game, clickCell)

        assertTrue("Неверно посчитан флаг корректного клика", result)
        verify(game).setExpectedCell(getCell(4))
        verify(game, never()).finish()
    }

    @Test
    fun execute_LastCell() = runBlockingTest {
        val game = mock<SchulteTableGame>()
        val clickCell = getCell(4)
        `when`(game.getCurrentExpectedCell()).thenReturn(getCell(4))
        `when`(game.getCurrentCells()).thenReturn(Matrix2D(arrayOf(
            arrayOf(getCell(1), getCell(2)),
            arrayOf(getCell(3), getCell(4))
        )))

        val result = sut.execute(game, clickCell)

        assertTrue("Неверно посчитан флаг корректного клика", result)
        verify(game, never()).setExpectedCell(any())
        verify(game).finish()
    }

    private fun getCell(num: Int) = SchulteTableCell(num.toString(), num)
}