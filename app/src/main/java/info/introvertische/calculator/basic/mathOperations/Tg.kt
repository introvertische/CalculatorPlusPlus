package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Tg : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.tan(firstValue.toDouble()).toString()
    }
}