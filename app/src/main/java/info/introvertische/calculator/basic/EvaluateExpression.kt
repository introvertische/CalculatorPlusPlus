package info.introvertische.calculator.basic

import info.introvertische.calculator.basic.Manager.MathManager
import java.math.RoundingMode
import java.text.DecimalFormat

class EvaluateExpression {

    private val constant = ConstantInit()
    private val mathManager = MathManager()

    fun start(mathExpression: String) : String {
        var mathExpr = mathExpression
        mathExpr = findParenthesesEngine(mathExpr)
        mathExpr = mainCalculate(mathExpr)
        return convertingToNumber(mathExpr)
    }

    private fun convertingToNumber(value: String) : String {
        val decimalPattern = DecimalFormat("#.#########")
        decimalPattern.roundingMode = RoundingMode.CEILING
        return try {
            val result = value.split(".")
            if (result.size == 1) {
                value.toInt().toString()
            } else {
                if (result[1].toLong() != 0.toLong())
                    decimalPattern.format(value.toDouble()).toString()
                else
                    result[0].toInt().toString()
            }
        } catch (exp: Exception) {
            value
        }
    }

    private fun mainCalculate(mathExpression: String) : String {
        var mathExpr = mathExpression

        while (!Regex(constant.finalResult).containsMatchIn(mathExpr) && mathExpr != constant.invalidValue) {

            when {
                mathExpr.indexOf(constant.degree) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.degree()), constant.degree)
                }
                mathExpr.indexOf(constant.factorial) != -1 -> {
                    //mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.division()), constant.division)
                }
                mathExpr.indexOf(constant.squareRoot) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.squareRoot()), constant.squareRoot)
                }
                mathExpr.indexOf(constant.cubeRoot) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.cubeRoot()), constant.cubeRoot)
                }
                mathExpr.indexOf(constant.arcsin) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.arcsin()), constant.arcsin)
                }
                mathExpr.indexOf(constant.arccos) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.arccos()), constant.arccos)
                }
                mathExpr.indexOf(constant.arctg) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.arctg()), constant.arctg)
                }
                mathExpr.indexOf(constant.sin) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.sin()), constant.sin)
                }
                mathExpr.indexOf(constant.cos) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.cos()), constant.cos)
                }
                mathExpr.indexOf(constant.tg) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.tg()), constant.tg)
                }
                mathExpr.indexOf(constant.log) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.log()), constant.log)
                }
                mathExpr.indexOf(constant.ln) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.ln()), constant.ln)
                }
                mathExpr.indexOf(constant.mod) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.mod()), constant.mod)
                }
                mathExpr.indexOf(constant.module) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.module()), constant.module)
                }
                Regex(constant.binaryOperations()).containsMatchIn(mathExpr) -> {
                    mathExpr = binaryOperations(mathExpr)
                }
                else -> {
                    mathExpr = constant.invalidValue
                    break
                }
            }

        }

        return mathExpr
    }

    private fun findParenthesesEngine(mathExpression: String) : String {
        var mathExpr = mathExpression

        try {

            while (mathExpr.indexOf("(") != -1) {
                val charMathExpr = mathExpr.toCharArray()
                val subExpression = arrayListOf<String>()

                while (true) {
                    val start = mathExpr.lastIndexOf("(") + 1
                    val end = mathExpr.substring(start - 1, mathExpr.length).indexOf(")") + start - 1

                    if (start >= end) throw Exception()

                    for (i in start until end) {
                        subExpression.add(charMathExpr[i].toString())
                    }
                    break;
                }

                val subExpressionString = subExpression.joinToString("")
                //val result = searchExpressionsEngine(subExpressionString)
                //mathExpr = replace(mathExpr, "($subExpressionString)", result)
                val result = mainCalculate(subExpressionString)
                mathExpr = replace(mathExpr, "($subExpressionString)", result)
            }

        } catch (exp: Exception) {
            mathExpr = constant.invalidValue
        }

        return mathExpr
    }

    private fun binaryOperations(mathExpression: String) : String {
        var mathExpr = mathExpression

        while (!Regex(constant.finalResult).containsMatchIn(mathExpr)) {

            if (mathExpr.indexOf(constant.multiplication) != -1 && mathExpr.indexOf(constant.division) != -1) {
                val indexMultiplication = mathExpression.indexOf(constant.multiplication)
                val indexDivision = mathExpression.indexOf(constant.division)

                if (indexMultiplication < indexDivision) {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.multiplication()), constant.multiplication)
                } else {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.division()), constant.division)
                }

            } else if (mathExpr.indexOf(constant.multiplication) != -1) {
                mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.multiplication()), constant.multiplication)
            } else if (mathExpr.indexOf(constant.division) != -1) {
                mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.division()), constant.division)
            } else if (mathExpr.indexOf(constant.plus) != -1) {
                mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.plus()), constant.plus)
            } else if (Regex(constant.minus).containsMatchIn(mathExpr)) {
                mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.minus()), constant.minus)
            } else {
                break
            }
        }

        return mathExpr
    }

    private fun calculate(mathExpression: String, expression: String, operator: String) : String {
        var result = ""

        try {

            val mathValueList = expression.split(operator)
            var firstValue = ""
            var secondValue = ""
            var isMinus = false

            if (mathValueList.size == 2 && mathValueList[0].isEmpty()) {
                firstValue = mathValueList[1]
            } else if (mathValueList.size == 2) {
                if (mathValueList[0].toDouble() < 0) isMinus = true
                firstValue = mathValueList[0]
                secondValue = mathValueList[1]
            } else if (mathValueList.size == 3) {
                firstValue = "-" + mathValueList[1]
                secondValue = mathValueList[2]
            } else {
                throw Exception()
            }

            when (operator) {
                constant.degree -> result = mathManager.degree.calculate(firstValue, secondValue)
                constant.squareRoot -> result = mathManager.squareRoot.calculate(firstValue)
                constant.multiplication -> result = mathManager.multiplication.calculate(firstValue, secondValue)
                constant.division -> result = mathManager.division.calculate(firstValue, secondValue)
                constant.plus -> result = mathManager.addition.calculate(firstValue, secondValue)
                constant.minus -> result = mathManager.subtraction.calculate(firstValue, secondValue)
                constant.factorial -> result = mathManager.factorial.calculate(firstValue)
                constant.cubeRoot -> result = mathManager.cubeRoot.calculate(firstValue)
                constant.sin-> result = mathManager.sin.calculate(firstValue)
                constant.cos -> result = mathManager.cos.calculate(firstValue)
                constant.tg -> result = mathManager.tg.calculate(firstValue)
                constant.arcsin -> result = mathManager.arcsin.calculate(firstValue)
                constant.arccos -> result = mathManager.arccos.calculate(firstValue)
                constant.arctg -> result = mathManager.arctg.calculate(firstValue)
                constant.log -> result = mathManager.log.calculate(firstValue)
                constant.ln -> result = mathManager.ln.calculate(firstValue)
                constant.module -> result = mathManager.module.calculate(firstValue)
                constant.mod -> result = mathManager.mod.calculate(firstValue, secondValue)
                else -> ""
            }

            if(isMinus && result.toDouble() >= 0) result = "+$result"

            return replace(mathExpression, expression, result)

        } catch (exp: Exception) {
           return constant.invalidValue
        }
    }

    private fun replace(mathExpr: String, what: String, how: String) : String {
        return mathExpr.replace(what, how)
    }

    private fun matchSearch(mathExpression: String, expressionTemplate: String) : String {
        return expressionTemplate.toRegex().find(mathExpression)?.value.orEmpty()
    }

}