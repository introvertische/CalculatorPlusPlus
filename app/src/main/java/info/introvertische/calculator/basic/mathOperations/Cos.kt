package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Cos : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.cos(firstValue.toDouble()).toString()
    }
}