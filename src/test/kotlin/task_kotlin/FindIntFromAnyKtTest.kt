package task_kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class FindIntFromAnyKtTest {
    @TestFactory
    fun `Поиск интов из списка Any на этапе компиляции`(): Collection<DynamicTest> {
        val list: List<Any> = listOf(1, 1.0, "ewew", '3', 2F, 3L, (1 to 4), 10)

        val expended = listOf(1, 10)

        return list.map {
            DynamicTest.dynamicTest("find int") {
                val actual = list.findInt()
                assertThat(actual).containsExactlyElementsOf(expended)
            }
        }.toList()
    }

    @TestFactory
    fun `Поиск интов из списка Any в рантайме`(): Collection<DynamicTest> {
        val list: List<Any> = listOf(1, 1.0, "ewew", '3', 2F, 3L, (1 to 4), 10)

        val expended = listOf(1, 10)

        return list.map {
            DynamicTest.dynamicTest("find int") {
                val actual = list.findInteger()
                assertThat(actual).containsExactlyElementsOf(expended)
            }
        }.toList()
    }
}


