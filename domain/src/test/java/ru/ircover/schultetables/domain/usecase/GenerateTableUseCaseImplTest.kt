package ru.ircover.schultetables.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import ru.ircover.schultetables.TimeManager
import ru.ircover.schultetables.domain.SchulteTableCell
import ru.ircover.schultetables.domain.SchulteTableSettings
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker

class GenerateTableUseCaseImplTest {
    private lateinit var sut: GenerateTableUseCaseImpl
    private lateinit var settingsWorker: SchulteTableSettingsWorker
    private lateinit var generateCellsOrderedListUseCase: GenerateCellsOrderedListUseCase
    private lateinit var timeManager: TimeManager

    @Before
    fun setup() {
        settingsWorker = mock()
        generateCellsOrderedListUseCase = mock()
        timeManager = mock()
        sut =  GenerateTableUseCaseImpl(settingsWorker, generateCellsOrderedListUseCase, timeManager)
    }

    @Test
    fun execute() {
        val settings = SchulteTableSettings(2, 3)
        val cells = (1..6).map { SchulteTableCell(it.toString(), it) }.toMutableList()
        val expectedStartCell = SchulteTableCell("1", 1)
        val nowMillis = 123L
        `when`(settingsWorker.get()).thenReturn(settings)
        `when`(generateCellsOrderedListUseCase.execute(settings)).thenReturn(cells)
        `when`(timeManager.getNowMillis()).thenReturn(nowMillis)

        val result = sut.execute()

        assertEquals("Неверное стартовое значение", expectedStartCell, result.startCell)

        assertEquals("Неверное количество итоговых столбцов",2, result.matrix.width)
        assertEquals("Неверное количество итоговых строк",3, result.matrix.height)
        for(x in 0..1) {
            for(y in 0..2) {
                val cell = result.matrix.get(x, y)
                assertTrue("В итоговой матрице значение $cell не найдено или встречается больше одного раза", cells.remove(cell))
            }
        }
        assertEquals("Неверное время старта", nowMillis, result.startTimeMillis)
    }
}