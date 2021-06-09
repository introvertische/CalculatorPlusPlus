package info.introvertische.calculator.basic

import info.introvertische.calculator.basic.enums.ConstantLibrary

class ConstantInit {

    var pi = ConstantLibrary.PI.const
        private set
    var e = ConstantLibrary.E.const
        private set
    var plus = ConstantLibrary.PLUS.const
        private set
    private val escapePlus = "\\" + plus
    var minus = ConstantLibrary.MINUS.const
        private set
    var multiplication = ConstantLibrary.MULTIPLICATION.const
        private set
    var division = ConstantLibrary.DIVISION.const
        private set
    var degree = ConstantLibrary.DEGREE.const
        private set
    var squareRoot = ConstantLibrary.SQUARE_ROOT.const
        private set
    var cubeRoot = ConstantLibrary.CUBE_ROOT.const
        private set
    var percent = ConstantLibrary.PERCENT.const
        private set
    var sin = ConstantLibrary.SIN.const
        private set
    var arcsin = ConstantLibrary.ARCSIN.const
        private set
    var cos = ConstantLibrary.COS.const
        private set
    var arccos = ConstantLibrary.ARCCOS.const
        private set
    var tg = ConstantLibrary.TG.const
        private set
    var arctg = ConstantLibrary.ARCTG.const
        private set
    var factorial = ConstantLibrary.FACTORIAL.const
        private set
    var mod = ConstantLibrary.MOD.const
        private set
    var module = ConstantLibrary.MODULE.const
        private set
    var log = ConstantLibrary.LOG.const
        private set
    var ln = ConstantLibrary.LN.const
        private set
    var invalidValue = ConstantLibrary.INVALID_VALUE.const
        private set
    var finalResult = "^$minus?$escapePlus?((\\d+\\.\\d+)|(\\d+))$"
        private set

    fun binaryOperations() : String {
        return createBinExpression("($escapePlus|$minus|$multiplication|$division)")
    }

    fun plus() : String {
        return createBinExpression(escapePlus)
    }

    fun minus() : String {
        return createBinExpression(minus)
    }

    fun multiplication() : String {
        return createBinExpression(multiplication)
    }

    fun division() : String {
        return createBinExpression(division)
    }

    fun degree() : String {
        return createBinExpression("\\" + degree)
    }

    fun mod() : String {
        return createBinExpression(mod)
    }

    private fun createBinExpression(sign: String) : String {
        return "$minus?((\\d+\\.\\d+)|(\\d+))$sign$minus?((\\d+\\.\\d+)|(\\d+))"
    }

    fun squareRoot() : String {
        return createSingleExpression(squareRoot)
    }

    fun cubeRoot() : String {
        return createSingleExpression(cubeRoot)
    }

    fun sin() : String {
        return createSingleExpression(sin)
    }

    fun arcsin() : String {
        return createSingleExpression(arcsin)
    }

    fun cos() : String {
        return createSingleExpression(cos)
    }

    fun arccos() : String {
        return createSingleExpression(arccos)
    }

    fun tg() : String {
        return createSingleExpression(tg)
    }

    fun arctg() : String {
        return createSingleExpression(arctg)
    }

    fun module() : String {
        return createSingleExpression(module, minus)
    }

    fun log() : String {
        return createSingleExpression(log)
    }

    fun ln() : String {
        return createSingleExpression(ln)
    }

    private fun createSingleExpression(sign: String, needMinus: String = "") : String {
        return "$sign$needMinus?((\\d+\\.\\d+)|(\\d+))"
    }

}