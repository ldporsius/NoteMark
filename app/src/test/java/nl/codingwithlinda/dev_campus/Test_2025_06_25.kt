package nl.codingwithlinda.dev_campus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.threetenabp.AndroidThreeTen.init
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.yield
import org.junit.After
import org.junit.Before
import org.junit.Test


class TestVM : ViewModel() {

    private val exhandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught in handler ${throwable.message}")
    }
    init {
        viewModelScope.launch(exhandler) {
           launch {
               throw NullPointerException("Oops is null")
            }
          launch {
                try {
                    throw RuntimeException("Oops again")

                }catch (e: Exception) {
                    ensureActive()
                    println("Caught in block : ${e.message}")
                }

            }
            launch {
                delay(1000)
                println("Still running?")
            }
        }

    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class Test_2025_06_25 {
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun test() = runBlocking{
        val vm = TestVM()
    }
}