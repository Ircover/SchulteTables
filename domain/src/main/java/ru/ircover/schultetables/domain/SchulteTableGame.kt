package ru.ircover.schultetables.domain

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

interface SchulteTableGame {
    fun getCells(): Flow<Matrix2D<SchulteTableCell>>
    fun getCurrentCells(): Matrix2D<SchulteTableCell>
    fun getExpectedCell(): Flow<SchulteTableCell>
    fun getCurrentExpectedCell(): SchulteTableCell
    fun getFinishEvents(): Flow<Unit>
    suspend fun refresh(data: SchulteTableStartData)
    suspend fun setExpectedCell(cell: SchulteTableCell)
    suspend fun finish()
}

class SchulteTableGameImpl : SchulteTableGame {
    private val matrix: MutableStateFlow<Matrix2D<SchulteTableCell>> =
        MutableStateFlow(Matrix2D.empty())
    private val expectedCell: MutableStateFlow<SchulteTableCell> =
            MutableStateFlow(SchulteTableCell.EMPTY)
    private val finishEvent: MutableSharedFlow<Unit> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun getCells(): Flow<Matrix2D<SchulteTableCell>> = matrix
    override fun getCurrentCells(): Matrix2D<SchulteTableCell> = matrix.value

    override fun getExpectedCell(): Flow<SchulteTableCell> = expectedCell
    override fun getCurrentExpectedCell() = expectedCell.value

    override fun getFinishEvents(): Flow<Unit> = finishEvent

    override suspend fun refresh(data: SchulteTableStartData) {
        matrix.emit(data.matrix)
        expectedCell.emit(data.startCell)
    }

    override suspend fun setExpectedCell(cell: SchulteTableCell) {
        expectedCell.emit(cell)
    }

    override suspend fun finish() {
        finishEvent.emit(Unit)
    }
}