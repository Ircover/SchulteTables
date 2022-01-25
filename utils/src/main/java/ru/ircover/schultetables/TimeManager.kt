package ru.ircover.schultetables

import javax.inject.Inject

interface TimeManager {
    fun getNowMillis(): Long
}

class TimeManagerImpl @Inject constructor() : TimeManager {
    override fun getNowMillis() = System.currentTimeMillis()
}