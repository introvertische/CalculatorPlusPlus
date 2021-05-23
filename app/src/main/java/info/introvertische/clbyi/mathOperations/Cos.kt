package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Cos : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.cos(firstValue.toDouble()).toString()
    }
}