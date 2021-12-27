package ru.ircover.schultetables.domain.usecase

import ru.ircover.schultetables.domain.SchulteTableCell
import ru.ircover.schultetables.domain.SchulteTableSettings

interface GenerateCellsOrderedListUseCase {
    fun execute(settings: SchulteTableSettings): List<SchulteTableCell>
}

class GenerateCellsOrderedListUseCaseImpl : GenerateCellsOrderedListUseCase {
    override fun execute(settings: SchulteTableSettings): List<SchulteTableCell> {
        val width = settings.columnsCount
        val height = settings.rowsCount
        return (1..width * height).mapTo(mutableListOf()) {
            SchulteTableCell(it.toString(), it)
        }
    }
}