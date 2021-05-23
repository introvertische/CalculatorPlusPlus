package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator

class Mod : MathOperator {
    override fun calculate(firstValue: String, secondValue: String): String {
        return Math.floorMod(firstValue.toInt(), secondValue.toInt()).toString()
    }
}