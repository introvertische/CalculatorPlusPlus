package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Ln : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.log(firstValue.toDouble()).toString()
    }
}