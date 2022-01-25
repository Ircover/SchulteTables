package ru.ircover.schultetables

import java.util.*

fun dateFromMillis(millis: Long): Calendar = Calendar.getInstance().apply {
    timeInMillis = millis
}