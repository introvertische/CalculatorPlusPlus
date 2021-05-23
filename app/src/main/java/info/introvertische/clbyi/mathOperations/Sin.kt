package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Sin : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.sin(firstValue.toDouble()).toString()
    }
}