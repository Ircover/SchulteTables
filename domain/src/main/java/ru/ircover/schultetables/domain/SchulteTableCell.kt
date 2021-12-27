package ru.ircover.schultetables.domain

data class SchulteTableCell(val text: String,
                            val order: Int) {
    companion object {
        val EMPTY = SchulteTableCell("", 0)
    }
}