package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Subtraction : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return (firstValue.toDouble() - secondValue.toDouble()).toString()
    }
}