package me.li2.android.hikotlin.kotlin

import me.li2.android.hikotlin.java.JavaMoney


// Introduction to Kotlin (Google I/O '17)
// https://www.youtube.com/watch?v=X1RVYt2QKQE
//
// Kotlin is now an officially supported language for Android. This session provides an introduction
// to the language, covering basic concepts and tips for developers to get started with it.


data class Money(val amount: Int, val currency: String)


/* tip: return type by default is Unit, which is kind of like void, but it's not. It's actually
 a object which is essentially a singleton, a single instance of an object. And if it is a unit,
 you don't have to put it there */
fun sendPayment(money: Money, message: String = "") {
    println("Sending money ${money.amount}")
}

fun main(args: Array<String>) {

    val ticket = Money(100, "$")
    val popcorn = ticket.copy(amount = 200, currency = "$")

    sendPayment(ticket)

    /* tip: this is doing is a property comparison one by one. It is not doing a point of comparison.
     For point of comparisons, we have the triple equal. */
    if (ticket != popcorn) {
        println("They are different")
    }
    
    /* tip: for consuming Java from Kotlin, IDE replace the getter/setter method with property. */
    val javaMoney = JavaMoney(100, "$")
    println("JavaMoney amount=${javaMoney.amount}")
}
