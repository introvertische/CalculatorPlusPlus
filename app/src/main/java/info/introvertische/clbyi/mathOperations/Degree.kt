package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator
import kotlin.math.pow

class Degree : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return (firstValue.toDouble().pow(secondValue.toDouble())).toString()
    }
}