package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Log : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.log10(firstValue.toDouble()).toString()
    }
}