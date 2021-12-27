package ru.ircover.schultetables.domain

data class SchulteTableStartData(val matrix: Matrix2D<SchulteTableCell>,
                                 val startCell: SchulteTableCell)