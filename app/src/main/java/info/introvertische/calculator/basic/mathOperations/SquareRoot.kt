package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class SquareRoot : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.sqrt(firstValue.toDouble()).toString()
    }
}