package me.li2.android.hikotlin.kotlin

import me.li2.android.hikotlin.java.JavaMoney


// Introduction to Kotlin (Google I/O '17)
// https://www.youtube.com/watch?v=X1RVYt2QKQE
//
// Kotlin is now an officially supported language for Android. This session provides an introduction
// to the language, covering basic concepts and tips for developers to get started with it.


data class Money(val amount: Int, val currency: String)


fun main(args: Array<String>) {

    val ticket = Money(100, "$")
    val popcorn = ticket.copy(amount = 200, currency = "$")

    /* tip: this is doing is a property comparison one by one. It is not doing a point of comparison.
     For point of comparisons, we have the triple equal. */
    if (ticket != popcorn) {
        println("They are different")
    }
    
    /* tip: for consuming Java from Kotlin, IDE replace the getter/setter method with property. */
    val javaMoney = JavaMoney(100, "$")
    println("JavaMoney amount=${javaMoney.amount}")
}
