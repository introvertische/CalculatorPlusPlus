package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Log : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.log10(firstValue.toDouble()).toString()
    }
}