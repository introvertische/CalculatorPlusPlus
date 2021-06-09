package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Arctg : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.atan(firstValue.toDouble()).toString()
    }
}