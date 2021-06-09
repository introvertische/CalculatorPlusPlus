package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class CubeRoot : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.cbrt(firstValue.toDouble()).toString()
    }
}