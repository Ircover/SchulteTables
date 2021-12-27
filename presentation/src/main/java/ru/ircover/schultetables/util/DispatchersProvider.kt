package ru.ircover.schultetables.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersProvider {
    fun main(): CoroutineDispatcher
}

class DispatchersProviderImpl : DispatchersProvider {
    override fun main() = Dispatchers.Main
}