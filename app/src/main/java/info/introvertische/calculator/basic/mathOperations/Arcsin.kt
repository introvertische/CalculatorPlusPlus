package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Arcsin : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.asin(firstValue.toDouble()).toString()
    }
}