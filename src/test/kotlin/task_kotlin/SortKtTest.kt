package task_kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class SortKtTest{

    @TestFactory
    fun `Сортировка, где список елементов не null`(): Collection<DynamicTest> {
        val list: List<List<Int?>> = listOf(
            listOf(4, 0, 1, null, 2, null, 3),
            listOf(null, null, 0, 2, 3, 1, 4),
            listOf(0, 1, 2, 3, 4, null, null),
            listOf(null, 4, 1, 3, 2, 0, null),
            listOf(null, null, 4, 3, 2, 1, 0),
        )

        val expended = listOf(0, 1, 2, 3, 4, null, null)

        return list.map {
            DynamicTest.dynamicTest("sort super bubble") {
                val actual = sortBubble(it)
                assertThat(actual).containsExactlyElementsOf(expended)
            }
        }.toList()
    }

    @Test
    fun `Сортировка, где список null`() {
        val list: List<Int?>? = null

        val expended = null
        val actual = sortBubble(list)

        assertThat(actual).isEqualTo(expended)
    }
}

