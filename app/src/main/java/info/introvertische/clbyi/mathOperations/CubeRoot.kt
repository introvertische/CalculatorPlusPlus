package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class CubeRoot : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.cbrt(firstValue.toDouble()).toString()
    }
}