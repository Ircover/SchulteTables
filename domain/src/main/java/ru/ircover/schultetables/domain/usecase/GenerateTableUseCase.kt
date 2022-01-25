package ru.ircover.schultetables.domain.usecase

import ru.ircover.schultetables.TimeManager
import ru.ircover.schultetables.domain.Matrix2D
import ru.ircover.schultetables.domain.SchulteTableSettingsWorker
import ru.ircover.schultetables.domain.SchulteTableStartData
import kotlin.random.Random

interface GenerateTableUseCase {
    fun execute(): SchulteTableStartData
}

class GenerateTableUseCaseImpl(private val settingsWorker: SchulteTableSettingsWorker,
                               private val generateCellsOrderedListUseCase: GenerateCellsOrderedListUseCase,
                               private val timeManager: TimeManager
) : GenerateTableUseCase {
    override fun execute(): SchulteTableStartData {
        val settings = settingsWorker.get()
        val cellsList = generateCellsOrderedListUseCase.execute(settings).toMutableList()
        val startCell = cellsList[0]
        val result = Array(settings.rowsCount) {
            Array(settings.columnsCount) {
                val cellIndex = Random.nextInt(cellsList.size)
                cellsList.removeAt(cellIndex)
            }
        }
        return SchulteTableStartData(Matrix2D(result), startCell, timeManager.getNowMillis())
    }
}