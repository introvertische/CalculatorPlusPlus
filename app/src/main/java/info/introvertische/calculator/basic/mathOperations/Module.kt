package info.introvertische.calculator.basic.mathOperations

import interfaces.MathOperator

class Module : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return (firstValue.toDouble() * -1).toString()
    }
}