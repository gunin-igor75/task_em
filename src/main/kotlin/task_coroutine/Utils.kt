package task_coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate


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


fun <T> Flow<T>.throttleLatestNotTimer(period: Long): Flow<T> = this
    .conflate()
    .transform {
        emit(it)
        delay(period)
    }

fun <T> Flow<T>.throttleLatestWithTimer(windowDuration: Long): Flow<T> {
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

