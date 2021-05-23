package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Subtraction : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return (firstValue.toDouble() - secondValue.toDouble()).toString()
    }
}