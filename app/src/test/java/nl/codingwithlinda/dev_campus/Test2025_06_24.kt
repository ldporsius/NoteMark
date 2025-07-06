package nl.codingwithlinda.dev_campus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import com.jakewharton.threetenabp.AndroidThreeTen.init
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.core.domain.model.Note
import nl.codingwithlinda.notemark.core.util.Result
import nl.codingwithlinda.notemark.feature_home.domain.NoteRepository
import nl.codingwithlinda.notemark.test_data_generators.FakeNoteRepository
import org.junit.Before
import org.junit.Test


abstract class BaseViewModel(repository: NoteRepository): ViewModel() {
    var myNote : Note? = null
    init {
        viewModelScope.launch {
            repository.getNote("1").also {
                if (it is Result.Success) {
                    myNote = it.data
                }
            }
        }
    }
}

class DerivedViewModel(repository: NoteRepository) : BaseViewModel(repository) {
    init {
        println("My note is $myNote")
    }
}

class TestOpenViewModel{
    private val fakeRepository = FakeNoteRepository()
    private val testViewModel = DerivedViewModel(fakeRepository)

    @Before
    fun setup() {
        runBlocking {
            fakeRepository.createNote(Note("1", "title", "content", dateCreated = "now", dateLastUpdated = "now"))
        }
    }

    @Test
    fun test(): Unit = runBlocking {
        // make sure note is in repository
        val noteRes = fakeRepository.getNote("1")
        assertThat(noteRes).isInstanceOf(Result.Success::class)

        // we expect the note to be in the view model
        val note = testViewModel.myNote
        println("testViewModel.myNote = ${testViewModel.myNote}") //prints null
        assertThat(note).isNotNull() //fails

    }
}



/////////////////////////////////////////////////////////////////////////////

open class BaseD9(val name: String = "Base") { init { println("Base init: name = $name") } }
class DerivedD9 : BaseD9("Derived") { }
//
open class Base {
    open val name: String = "Base"

    open fun printName() {
        println("Base class prints name: $name")
    }
//    init {
//        println("Base init: name = $name")
//    }
}

class Derived : Base() {
    override val name: String = "Derived"
}
class DerivedPrint : Base() {
    override val name: String = "Derived"
    override fun printName() {
        println("Derived class prints name: $name")
    }
}
fun main() {
    Derived().printName()
    DerivedPrint().printName()
}
class Test2025_06_24 {
    @Test
    fun test() {
        //main()
        DerivedD9()
    }

}