package task_kotlin

fun sortBubble(list: List<Int?>?): List<Int?>? {
    if (list == null) return null
    val data = list.toMutableList()
    var low = 0
    var up = data.lastIndex
    var last: Int
    while (low < up) {
        last = -1
        for (i in low..<up) {
            if (data[i] > (data[i + 1])) {
                data.swap(i, i + 1)
                last = i
            }
        }
        up = last
        if (last == -1) break
        last = data.size
        for (i in up - 1 downTo low) {
            if (data[i] > data[i + 1]) {
                data.swap(i, i + 1)
                last = i
            }
        }
        low = last + 1
    }
    return data
}

private fun MutableList<Int?>.swap(i1: Int, i2: Int) {
    this[i1] = this[i2].also { this[i2] = this[i1] }
}

private operator fun Int?.compareTo(other: Int?): Int {
    if (this != null && other != null) {
        return this.compareTo(other)
    }
    if (this == null && other == null) return 0
    if (this == null) return 1
    return -1
}