package task_kotlin


fun List<Any>.findInt():List<Int?> = this.filterIsInstance<Int>()

fun List<Any>.findInteger():List<Int> = filter { it is Int }.map { it as Int }
