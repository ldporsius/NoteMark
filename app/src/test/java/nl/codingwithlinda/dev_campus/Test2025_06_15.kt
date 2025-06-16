package nl.codingwithlinda.dev_campus

import org.junit.Test

class Test2025_06_15 {

    fun getLastPositiveElement(array: List<Int>): Int {
        require(array.isNotEmpty()) { "Array must not be empty" }

        val nonNegative = array.filter { it >= 0 }
        val lastIndex = nonNegative.lastIndex

        println("--inside getLastPositiveElement-- lastIndex == $lastIndex")

        return nonNegative[lastIndex]
    }

    fun main() {
        val arr = listOf(1, 2, 3, 4, 5)
        println("Last element: ${getLastPositiveElement(arr)}")
    }

    fun callLastPositiveWithNegativeNumbersOnly(){
        val arr = listOf(-42)
        println("Last element: ${getLastPositiveElement(arr)}")
    }

    @Test
    fun test(){

        callLastPositiveWithNegativeNumbersOnly()
    }
}