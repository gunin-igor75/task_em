package task_coroutine

import com.hoc081098.flowext.throttleTime
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class UtilsKtTest {

    private fun getObservableData(): Observable<Int> {
        return Observable.create { emitter ->
            emitter.onNext(1)
            Thread.sleep(90)
            emitter.onNext(2)
            Thread.sleep(90)
            emitter.onNext(3)
            Thread.sleep(1010)
            emitter.onNext(4)
            Thread.sleep(1010)
            emitter.onNext(5)
            Thread.sleep(2000)
            emitter.onNext(6)
            Thread.sleep(90)
            emitter.onNext(7)
            Thread.sleep(1010)
            emitter.onNext(8)
            Thread.sleep(80)
            emitter.onNext(9)
            emitter.onComplete()
        }
    }

    private val testFlow = flow {
        emit(1)
        delay(90)
        emit(2)
        delay(90)
        emit(3)
        delay(1010)
        emit(4)
        delay(1010)
        emit(5)
        delay(2000)
        emit(6)
        delay(90)
        emit(7)
        delay(1010)
        emit(8)
        delay(80)
        emit(9)
    }

    @Test
    fun `check throttleLatest rx java`() {
        val expected = listOf(1, 3, 4, 5, 6, 7)

        val obs = getObservableData().throttleLatest(1, TimeUnit.SECONDS)
            .toList()
            .subscribe { actual ->
                assertThat(actual).containsExactlyElementsOf(expected)
            }

        obs.dispose()
    }

    @Test
    fun `check throttleFirst`(): Unit = runBlocking {

        val modifierFlow = testFlow.throttleFirst(1000)

        val actual = async { modifierFlow.toList() }.await()

        val expected = listOf(1, 4, 5, 6, 8)

        assertThat(actual).containsExactlyElementsOf(expected)
    }

    @Test
    fun `check throttleLatestWithTimer`(): Unit = runBlocking {

        val modifierFlow = testFlow.throttleLatestWithTimer(1000)

        val actual = async { modifierFlow.toList() }.await()

        val expected = listOf(1, 3, 4, 5, 7)

        assertThat(actual).containsExactlyElementsOf(expected)
    }
}



