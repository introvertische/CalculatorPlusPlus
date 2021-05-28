package info.introvertische.clbyi

import info.introvertische.clbyi.enumLib.ConstantLib
import info.introvertische.clbyi.enumLib.FormatLib
import info.introvertische.info.introvertische.clbyi.MathManager
import java.math.RoundingMode
import java.text.DecimalFormat

class Engine(
    private val lexicalAnalyzer: LexicalAnalyzer,
    private val mathManager: MathManager,
    private val mathValidator: MathValidator
    ) {

    private fun replace(mathExpr: String, what: String, how: String) : String {
        return mathExpr.replace(what, how)
    }

    private fun evaluate(mathExpr: String, expr: String, flag: String): String {
        try {
            val arrayValue = expr.split(flag)
            val firstFlag: String
            val secondFlag: String
            var resultValue = ""
            if (arrayValue.size == 2 && arrayValue[0] == "") {
                firstFlag = arrayValue[1]
                secondFlag = ""
            } else if (arrayValue.size == 2) {
                firstFlag = arrayValue[0]
                secondFlag = arrayValue[1]
            } else {
                firstFlag = "-" + arrayValue[1]
                secondFlag = arrayValue[2]
            }
            when (flag) {
                ConstantLib.DEGREE.const -> resultValue = mathManager.degree.calculate(firstFlag, secondFlag)
                ConstantLib.SQUARE_ROOT.const -> resultValue = mathManager.squareRoot.calculate(firstFlag)
                ConstantLib.MULTIPLICATION.const -> resultValue = mathManager.multiplication.calculate(firstFlag, secondFlag)
                ConstantLib.DIVISION.const -> resultValue = mathManager.division.calculate(firstFlag, secondFlag)
                ConstantLib.PLUS.const -> resultValue = mathManager.addition.calculate(firstFlag, secondFlag)
                ConstantLib.MINUS.const -> resultValue = mathManager.subtraction.calculate(firstFlag, secondFlag)
                ConstantLib.FACTORIAL.const -> resultValue = mathManager.factorial.calculate(firstFlag)
                ConstantLib.CUBE_ROOT.const -> resultValue = mathManager.cubeRoot.calculate(firstFlag)
                ConstantLib.SIN.const -> resultValue = mathManager.sin.calculate(firstFlag)
                ConstantLib.COS.const -> resultValue = mathManager.cos.calculate(firstFlag)
                ConstantLib.TG.const -> resultValue = mathManager.tg.calculate(firstFlag)
                ConstantLib.ARCSIN.const -> resultValue = mathManager.arcsin.calculate(firstFlag)
                ConstantLib.ARCCOS.const -> resultValue = mathManager.arccos.calculate(firstFlag)
                ConstantLib.ARCTG.const -> resultValue = mathManager.arctg.calculate(firstFlag)
                ConstantLib.LOG.const -> resultValue = mathManager.log.calculate(firstFlag)
                ConstantLib.LN.const -> resultValue = mathManager.ln.calculate(firstFlag)
                ConstantLib.MODULE.const -> resultValue = mathManager.module.calculate(firstFlag)
                ConstantLib.MOD.const -> resultValue = mathManager.mod.calculate(firstFlag, secondFlag)
                else -> ""
            }
            return if (firstFlag.indexOf("-") != -1 && resultValue.indexOf("-") == -1) {
                replace(mathExpr, expr, "+$resultValue")
            } else {
                replace(mathExpr, expr, resultValue)
            }

        } catch (exp: Exception) {
            return ConstantLib.INVALID_VALUE.const
        }
    }

    private fun findParenthesesEngine(mathExpression: String) : String {
        var mathExpr = mathExpression
        while (mathExpr.indexOf("(") != -1) {
            val charMathExpr = mathExpr.toCharArray()
            val subExpression = arrayListOf<String>()



            while (true) {
                val start = mathExpr.lastIndexOf("(") + 1
                val end = mathExpr.substring(start - 1, mathExpr.length).indexOf(")") + start - 1
                for (i in start until end) {
                    subExpression.add(charMathExpr[i].toString())
                }
                break;
            }


            val subExpressionString = subExpression.joinToString("")
            val result = searchExpressionsEngine(subExpressionString)
            mathExpr = replace(mathExpr, "($subExpressionString)", result)
        }
        return mathExpr
    }

    private fun searchExpressionsEngine(mathExpression: String) : String {
        var newMathExpr = findParenthesesEngine(mathExpression)
        var isFind = true
        while (isFind) {
            when {
                newMathExpr.indexOf(ConstantLib.PI.const) != -1 -> {
                    newMathExpr = newMathExpr.replace(ConstantLib.PI.const, Math.PI.toString())
                }
                newMathExpr.indexOf(ConstantLib.E.const) != -1 -> {
                    newMathExpr = newMathExpr.replace(ConstantLib.E.const, Math.E.toString())
                }
                newMathExpr.indexOf(ConstantLib.DEGREE.const) != -1 -> {
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchDegree(newMathExpr),
                            ConstantLib.DEGREE.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.FACTORIAL.const) != -1 -> { //Factorial
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchFactorial(newMathExpr),
                            ConstantLib.FACTORIAL.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.SQUARE_ROOT.const) != -1 -> {
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchSquareRoot(newMathExpr),
                            ConstantLib.SQUARE_ROOT.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.CUBE_ROOT.const) != -1 -> { //cbrt
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchCubeRoot(newMathExpr),
                            ConstantLib.CUBE_ROOT.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.ARCSIN.const) != -1 -> { //arcsin
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchArcsin(newMathExpr),
                            ConstantLib.ARCSIN.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.ARCCOS.const) != -1 -> { //arccos
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchArccos(newMathExpr),
                            ConstantLib.ARCCOS.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.ARCTG.const) != -1 -> { //arctg
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchArctg(newMathExpr),
                            ConstantLib.ARCTG.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.SIN.const) != -1 -> { //sin
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchSin(newMathExpr),
                            ConstantLib.SIN.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.COS.const) != -1 -> { //cos
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchCos(newMathExpr),
                            ConstantLib.COS.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.TG.const) != -1 -> { //tg
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchTg(newMathExpr),
                            ConstantLib.TG.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.LOG.const) != -1 -> { //log
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchLog(newMathExpr),
                            ConstantLib.LOG.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.LN.const) != -1 -> { //ln
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchLn(newMathExpr),
                            ConstantLib.LN.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.MOD.const) != -1 -> { // mod
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchMod(newMathExpr),
                            ConstantLib.MOD.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.MODULE.const) != -1 -> { // module
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchModule(newMathExpr),
                            ConstantLib.MODULE.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.MULTIPLICATION.const) != -1 -> {
                    newMathExpr = evaluate(newMathExpr,
                            lexicalAnalyzer.searchMultiplication(newMathExpr),
                            ConstantLib.MULTIPLICATION.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.DIVISION.const) != -1 -> {
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchDivision(newMathExpr),
                            ConstantLib.DIVISION.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.PLUS.const) != -1 -> {
                    newMathExpr = evaluate(
                            newMathExpr,
                            lexicalAnalyzer.searchPlus(newMathExpr),
                            ConstantLib.PLUS.const
                    )
                }
                newMathExpr.indexOf(ConstantLib.MINUS.const) != -1 -> {
                    if (lexicalAnalyzer.searchFinalResult(newMathExpr)) {
                        isFind = false
                    } else {
                        newMathExpr = evaluate(
                                newMathExpr,
                                lexicalAnalyzer.searchMinus(newMathExpr),
                                ConstantLib.MINUS.const
                        )
                    }
                }
                else -> {
                    isFind = false
                }
            }

            if (lexicalAnalyzer.searchFinalResult(newMathExpr)) {
                isFind = false
            }
        }
        return newMathExpr
//        return if (newMathExpr == mathExpression)
//            ConstantLib.INVALID_VALUE.const
//        else
//            newMathExpr
    }

    private fun checkForInt(value: String) : String {
        return try {
            val decimalFormat = DecimalFormat(FormatLib.DECIMAL_FORMAT.value)
            decimalFormat.roundingMode = RoundingMode.CEILING
            val result = value.split(".")
            if (result[1].toLong() != 0.toLong())
                decimalFormat.format(value.toDouble()).toString()
            else
                result[0]
        } catch (exp: Exception) {
            value
        }
    }

    fun toStart(mathExpression: String) : String {
        return if (mathValidator.validation(mathExpression))
            checkForInt(searchExpressionsEngine(mathExpression))
        else
            ConstantLib.INVALID_EXPRESSION.const
    }
}