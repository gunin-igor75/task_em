package task_coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UtilsKtTest {

    @Test
    fun `check throttleFirst`(): Unit = runBlocking {

        val testFlow = flow<String> {
            emit("A")

            delay(500)
            emit("B");

            delay(200);
            emit("C");

            delay(800);
            emit("D");

            delay(600);
            emit("E");
        }
        val modifierFlow = testFlow.throttleFirst(1000)

        val actual = async { modifierFlow.toList() }.await()

        val expected = listOf("A", "D")

        assertThat(actual).containsExactlyElementsOf(expected)
    }

    @Test
    fun `check throttleLatest`(): Unit = runBlocking {

        val testFlow = flow<String> {
            emit("A");

            delay(500);
            emit("B");

            delay(200);
            emit("C");

            delay(800);
            emit("D");

            delay(600);
            emit("E");
        }
        val modifierFlow = testFlow.throttleLatest(1000)

        val actual = async { modifierFlow.toList() }.await()
        println(actual)
        val expected = listOf("A", "C", "D")

        assertThat(actual).containsExactlyElementsOf(expected)
    }

}



