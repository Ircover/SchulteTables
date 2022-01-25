package ru.ircover.schultetables.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatchersProvider {
    fun main(): CoroutineDispatcher
}

class DispatchersProviderImpl @Inject constructor() : DispatchersProvider {
    override fun main() = Dispatchers.Main
}