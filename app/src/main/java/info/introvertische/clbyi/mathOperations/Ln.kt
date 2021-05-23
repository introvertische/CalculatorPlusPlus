package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Ln : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.log(firstValue.toDouble()).toString()
    }
}