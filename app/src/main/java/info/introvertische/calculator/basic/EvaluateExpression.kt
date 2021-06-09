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

        while (!Regex(constant.finalResult).containsMatchIn(mathExpr)) {

            when {
                mathExpression.indexOf(constant.degree) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.degree()), constant.degree)
                }
                mathExpression.indexOf(constant.factorial) != -1 -> {
                    //mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.division()), constant.division)
                }
                mathExpression.indexOf(constant.squareRoot) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.squareRoot()), constant.squareRoot)
                }
                mathExpression.indexOf(constant.cubeRoot) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.cubeRoot()), constant.cubeRoot)
                }
                mathExpression.indexOf(constant.arcsin) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.arcsin()), constant.arcsin)
                }
                mathExpression.indexOf(constant.arccos) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.arccos()), constant.arccos)
                }
                mathExpression.indexOf(constant.arctg) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.arctg()), constant.arctg)
                }
                mathExpression.indexOf(constant.sin) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.sin()), constant.sin)
                }
                mathExpression.indexOf(constant.cos) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.cos()), constant.cos)
                }
                mathExpression.indexOf(constant.tg) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.tg()), constant.tg)
                }
                mathExpression.indexOf(constant.log) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.log()), constant.log)
                }
                mathExpression.indexOf(constant.ln) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.ln()), constant.ln)
                }
                mathExpression.indexOf(constant.mod) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.mod()), constant.mod)
                }
                mathExpression.indexOf(constant.module) != -1 -> {
                    mathExpr = calculate(mathExpr, matchSearch(mathExpr, constant.module()), constant.module)
                }
                Regex(constant.binaryOperations()).containsMatchIn(mathExpression) -> {
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
            //val result = searchExpressionsEngine(subExpressionString)
            //mathExpr = replace(mathExpr, "($subExpressionString)", result)
            val result = mainCalculate(subExpressionString)
            mathExpr = replace(mathExpr, "($subExpressionString)", result)
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
        val mathValueList = expression.split(operator)
        var firstValue = ""
        var secondValue = ""
        var isMinus = false
        var result = ""

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
            TODO()
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
    }

    private fun replace(mathExpr: String, what: String, how: String) : String {
        return mathExpr.replace(what, how)
    }

    private fun matchSearch(mathExpression: String, expressionTemplate: String) : String {
        return expressionTemplate.toRegex().find(mathExpression)?.value.orEmpty()
    }

}