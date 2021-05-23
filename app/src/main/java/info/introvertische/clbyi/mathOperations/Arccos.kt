package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Arccos : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.acos(firstValue.toDouble()).toString()
    }
}