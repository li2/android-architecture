package me.li2.android.hikotlin.kotlin

import java.util.*

/** + */
fun operatorPlus(input: Int): Int {
    return input + 6
}

/** - */
fun operatorMinus(input: Int): Int {
    return input - 3
}

/** * */
fun operatorMultiply(input: Int): Int {
    return input * 3
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
fun genOperatorsCombinations(operators: Array<String>): List<List<String>> {

    val results = ArrayList<List<String>>()

    /*COMBINATIONS OF LENGTH THREE*/
    for (i in operators.indices) {
        for (j in operators.indices) {
            for (k in operators.indices) {
                for (l in operators.indices) {
                    for (m in operators.indices) {
                        for (n in operators.indices) {
                            for (o in operators.indices) {
                                val element = ArrayList<String>()
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

//fun combination(operators: List<Operator>): List<List<Operator>> {
//    List<>
//}

fun calculate(input: Int, operator: String): Int {
    return when(operator) {
        "+" -> operatorPlus(input)
        "-" -> operatorMinus(input)
        "r" -> operatorReverse(input)
        "<<" -> operatorShift(input)
        else -> {
            0
        }
    }
}

fun chainCalculate(input: Int, operators: List<String>): Int {
    var result = input
    for (operator in operators) {
        result = calculate(result, operator)
    }
    return result
}

fun main(args: Array<String>) {
//    var moves = 7
    var input = 0
    var goal = 28
    var result: Int
    var operators = arrayOf("+", "-", "r", "<<")

    var combinations = genOperatorsCombinations(operators)
    for (operators in combinations) {
        result = chainCalculate(input, operators)
        if (result == goal) {
            println("Goal $goal achieved with operators $operators")
        }
    }
}
