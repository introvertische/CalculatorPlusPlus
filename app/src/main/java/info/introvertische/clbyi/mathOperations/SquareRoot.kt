package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class SquareRoot : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.sqrt(firstValue.toDouble()).toString()
    }
}