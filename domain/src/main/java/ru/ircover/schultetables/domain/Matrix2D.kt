package ru.ircover.schultetables.domain

import java.lang.IllegalArgumentException

class Matrix2D<T>(private val matrix: Array<Array<T>>) {
    companion object {
        inline fun <reified T> empty() = Matrix2D<T>(arrayOf())
    }

    val height: Int = matrix.size
    val width: Int = if(height > 0) {
        val widths = matrix.map { it.size }.distinct()
        if (widths.size != 1) {
            throw IllegalArgumentException("Matrix size should be flat")
        }
        widths.first()
    } else {
        0
    }

    fun get(x: Int, y: Int): T = matrix[y][x]

    fun flatMap(): List<T> {
        return matrix.flatMap { it.asIterable() }
    }
}