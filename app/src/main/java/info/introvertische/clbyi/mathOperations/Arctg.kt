package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Arctg : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.atan(firstValue.toDouble()).toString()
    }
}