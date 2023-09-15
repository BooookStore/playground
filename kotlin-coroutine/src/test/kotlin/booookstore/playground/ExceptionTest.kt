package booookstore.playground

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExceptionTest {

    @Test
    fun exceptionInLaunch() {
        assertThrows<IndexOutOfBoundsException> {
            // 子コルーチンで発生した例外は親コルーチンに伝搬する。
            runBlocking {
                launch {
                    println("launch throw exception")
                    throw IndexOutOfBoundsException()
                }
                delay(1000L)
                println("not reach here")
            }
        }
    }

    @Test
    fun exceptionInLaunchTryCatch() {
        assertThrows<IndexOutOfBoundsException> {
            // 子コルーチンで発生した例外を try-catch で補足することはできない。
            runBlocking {
                try {
                    launch {
                        println("launch throw exception")
                        throw IndexOutOfBoundsException()
                    }
                } catch (exception: IndexOutOfBoundsException) {
                    println("catch exception")
                }
            }
        }
    }

    @Test
    fun exceptionInLaunchRunBlockingTryCatch() {
        val caughtException = assertThrows<Exception> {
            // ルートコルーチンから try-catch で例外を補足できる
            try {
                runBlocking {
                    launch {
                        println("launch throw exception")
                        throw IndexOutOfBoundsException()
                    }
                }
            } catch (exception: IndexOutOfBoundsException) {
                throw Exception(exception)
            }
        }

        assertEquals(caughtException.cause?.javaClass, IndexOutOfBoundsException::class.java)
    }

}