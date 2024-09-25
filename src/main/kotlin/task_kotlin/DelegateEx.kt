package task_kotlin

import kotlin.concurrent.timer
import kotlin.reflect.KProperty


private const val NAME = "Timer"

class MyDelegate {


    private var count: Int = 0

    init {
        timer(NAME, period = PERIOD) {
            println("$NAME $count")
            count += 3
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return count
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        count = value
    }
}

private const val PERIOD = 3000L
