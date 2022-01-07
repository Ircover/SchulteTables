package ru.ircover.schultetables.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("SuspendFunctionOnCoroutineScope")
suspend fun <T> CoroutineScope.launchCollect(flow: Flow<T>,
                                             action: suspend (T) -> Unit) =
    launch {
        flow.collect(action)
    }