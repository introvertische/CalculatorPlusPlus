package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Arccos : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.acos(firstValue.toDouble()).toString()
    }
}