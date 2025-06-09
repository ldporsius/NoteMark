package nl.codingwithlinda.notemark.tests.exercises

import org.junit.Test

class ChildrenAtLunchTest {

    @Test
    fun buySomething() {
        val childrenAtLunch = ChildrenAtLunch()
        childrenAtLunch.buySomething(Quarter)
    }

}