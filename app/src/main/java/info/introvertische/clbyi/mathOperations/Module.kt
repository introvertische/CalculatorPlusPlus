package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Module : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return (firstValue.toDouble() * -1).toString()
    }
}