package me.li2.android.hikotlin.kotlin

import me.li2.android.hikotlin.kotlin.Operator.*
import java.util.*


/** + */
fun operatorPlus(input: Int): Int {
    return input + MY_PLUS
}

/** - */
fun operatorMinus(input: Int): Int {
    return input + MY_MINUS
}

/** x */
fun operatorMultiply(input: Int): Int {
    return input * MY_MULTIPLY
}

/** / */
fun operatorDivider(input: Int): Int {
    return input / MY_DIVIDER
}

/** reverse : 12 to 21 */
fun operatorReverse(input: Int): Int {
    var num = input
    var reversed = 0

    while (num != 0) {
        val digit = num % 10
        reversed = reversed * 10 + digit
        num /= 10
    }

    return reversed
}

/** +/- */
fun operatorConvert(input: Int): Int {
    return -input
}

/** << */
fun operatorShift(input: Int): Int {
    return input / 10
}

/**
 * THE METHOD THAT TAKES AN ARRAY OF STRINGS AND PRINTS THE
 * POSSIBLE COMBINATIONS.
 */
fun genOperatorsCombinations(operators: Array<Operator>): List<List<Operator>> {

    val results = ArrayList<List<Operator>>()

    /*COMBINATIONS OF LENGTH THREE*/
    for (i in operators.indices) {
        for (j in operators.indices) {
            for (k in operators.indices) {
                for (l in operators.indices) {
                    for (m in operators.indices) {
                        for (n in operators.indices) {
                            for (o in operators.indices) {
                                val element = ArrayList<Operator>()
                                element.add(operators[i])
                                element.add(operators[j])
                                element.add(operators[k])
                                element.add(operators[l])
                                element.add(operators[m])
                                element.add(operators[n])
                                element.add(operators[o])
                                results.add(element)
                            }
                        }
                    }
                }
            }
        }
    }

    return results
}

enum class Operator(val alias: String) {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("x"),
    DIVIDE("/"),
    REVERSE("Reverse"),
    CONVERT("+/-"),
    SHIFT("<<");
}

fun calculate(input: Int, operator: Operator): Int {
    return when(operator) {
        Operator.PLUS -> operatorPlus(input)
        Operator.MINUS-> operatorMinus(input)
        Operator.MULTIPLY -> operatorMultiply(input)
        Operator.REVERSE -> operatorReverse(input)
        Operator.CONVERT -> operatorConvert(input)
        Operator.SHIFT -> operatorShift(input)
        else -> {
            0
        }
    }
}

fun chainCalculate(input: Int, operators: List<Operator>): Int {
    var result = input
    for (operator in operators) {
        result = calculate(result, operator)
    }
    return result
}

fun printGoal(operators: List<Operator>) {
    var result = ""
    for (operator in operators) {
        result += " ${operator.alias}"
    }
    println("Goal $MY_GOAL achieved with operators: $result")
}

var MY_MOVES: Int = 7
var MY_INIT: Int = 0
var MY_GOAL: Int = 28
var MY_OPERATORS: Array<Operator> = arrayOf(PLUS, MINUS, REVERSE, SHIFT)
var MY_PLUS: Int = 6
var MY_MINUS: Int = -3
var MY_MULTIPLY: Int = 2
var MY_DIVIDER: Int = 2

// Goal 28 achieved with operators:  + + << + + Reverse -

fun main(args: Array<String>) {

    var result: Int
    var combinations = genOperatorsCombinations(MY_OPERATORS)

    for (operators in combinations) {
        result = chainCalculate(MY_INIT, operators)
        if (result == MY_GOAL) {
            printGoal(operators)
            break
        }
    }
}
