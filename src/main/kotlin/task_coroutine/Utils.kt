package task_coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import java.util.*
import kotlin.Long
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.let


fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastTime = 0L
    collect { elem ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > windowDuration) {
            lastTime = currentTime
            emit(elem)
        }
    }
}


fun <T> Flow<T>.throttleLatest(windowDuration: Long): Flow<T> {
    return channelFlow {
        var lastValue: T?
        var timer: Timer? = null
        collect { elem ->
            lastValue = elem
            if (timer == null) {
                timer = Timer().apply {
                    scheduleAtFixedRate(
                        delay = 0,
                        period = windowDuration
                    ) {
                        val value = lastValue
                        lastValue = null
                        value?.let { launch { send(value) } } ?: {
                            timer?.cancel()
                            timer = null
                        }
                    }
                }
            }
        }
        onCompletion {
            timer?.cancel()
            timer = null
            lastValue = null
        }
    }
}