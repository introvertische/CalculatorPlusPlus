package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Sin : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.sin(firstValue.toDouble()).toString()
    }
}