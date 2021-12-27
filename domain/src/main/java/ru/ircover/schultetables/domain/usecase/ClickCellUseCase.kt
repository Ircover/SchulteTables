package ru.ircover.schultetables.domain.usecase

import ru.ircover.schultetables.domain.SchulteTableCell
import ru.ircover.schultetables.domain.SchulteTableGame

interface ClickCellUseCase {
    suspend fun execute(game: SchulteTableGame, cell: SchulteTableCell): Boolean
}

class ClickCellUseCaseImpl : ClickCellUseCase {
    override suspend fun execute(game: SchulteTableGame, cell: SchulteTableCell): Boolean {
        return if(cell == game.getCurrentExpectedCell()) {
            val nextCell = game.getCurrentCells()
                               .flatMap()
                               .filter { it.order > cell.order }
                               .minByOrNull { it.order }
            if(nextCell != null) {
                game.setExpectedCell(nextCell)
            } else {
                game.finish()
            }
            true
        } else {
            false
        }
    }
}