package info.introvertische.clbyi.mathOperations

import info.introvertische.clbyi.interfaces.MathOperator
import java.lang.Exception
import kotlin.math.roundToInt

class Mod : MathOperator {

    override fun calculate(firstValue: String, secondValue: String): String {
        val firstListValue = firstValue.toDouble().toString().split(".")
        val secondListValue = secondValue.toDouble().toString().split(".")

        //val a = Math.floorMod(firstValue.toDouble().roundToInt(), secondValue.toDouble().roundToInt()).toString()
        //return Math.floorMod(firstValue.toDouble().roundToInt(), secondValue.toDouble().roundToInt()).toString()

        var mod = Math.floorMod(firstListValue[0].toInt(), secondListValue[0].toInt()).toString()

        try {
            if (firstListValue.size == 2 && secondListValue.size == 2 && firstListValue[1].toInt() != 0 && secondListValue[1].toInt() != 0) {
                mod += (firstListValue[1].toInt() % secondListValue[1].toInt()).toString()
            } else if (firstListValue.size == 2 && secondListValue.size == 1) {
                mod += firstListValue[1]
            } else {
                var value = firstValue.toDouble() % secondValue.toDouble()
                while (value < 0) {
                    value += secondValue.toDouble()
                }
                mod = value.toString()
            }
        } catch (exp: Exception) {

        }

        return mod
    }
}