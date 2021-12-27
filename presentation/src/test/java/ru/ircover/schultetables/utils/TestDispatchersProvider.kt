package ru.ircover.schultetables.utils

import kotlinx.coroutines.CoroutineDispatcher
import ru.ircover.schultetables.util.DispatchersProvider

class TestDispatchersProvider(private val testDispatcher: CoroutineDispatcher) :
    DispatchersProvider {
    override fun main() = testDispatcher
}