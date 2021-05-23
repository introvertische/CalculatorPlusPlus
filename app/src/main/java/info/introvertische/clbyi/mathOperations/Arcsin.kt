package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Arcsin : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.asin(firstValue.toDouble()).toString()
    }
}