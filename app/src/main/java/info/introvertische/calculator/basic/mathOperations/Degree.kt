package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator
import kotlin.math.pow

class Degree : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return (firstValue.toDouble().pow(secondValue.toDouble())).toString()
    }
}