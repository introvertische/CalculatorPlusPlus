package info.introvertische.clbyi

import info.introvertische.clbyi.enumLib.MathLib

class LexicalAnalyzer() {

    fun searchPlus(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.PLUS.mathOperation)
    }

    fun searchMinus(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.MINUS.mathOperation)
    }

    fun searchMultiplication(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.MULTIPLICATION.mathOperation)
    }

    fun searchDivision(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.DIVISION.mathOperation)
    }

    fun searchDegree(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.DEGREE.mathOperation)
    }

    fun searchSquareRoot(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.SQUARE_ROOT.mathOperation)
    }

    fun searchCubeRoot(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.CUBE_ROOT.mathOperation)
    }

    fun searchSin(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.SIN.mathOperation)
    }

    fun searchArcsin(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.ARCSIN.mathOperation)
    }

    fun searchCos(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.COS.mathOperation)
    }

    fun searchArccos(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.ARCCOS.mathOperation)
    }

    fun searchTg(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.TG.mathOperation)
    }

    fun searchArctg(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.ARCTG.mathOperation)
    }

    fun searchFactorial(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.FACTORIAL.mathOperation)
    }

    fun searchMod(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.MOD.mathOperation)
    }

    fun searchModule(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.MODULE.mathOperation)
    }

    fun searchLn(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.LN.mathOperation)
    }

    fun searchLog(mathExpression: String): String {
        return matchSearch(mathExpression, MathLib.LOG.mathOperation)
    }

    fun searchFinalResult(mathExpression: String) : Boolean {
        return MathLib.FINAL_RESULT.mathOperation.toRegex().find(mathExpression)?.value.orEmpty().isNotEmpty()
    }

    private fun matchSearch(mathExpression: String, expressionTemplate: String) : String {
        return expressionTemplate.toRegex().find(mathExpression)?.value.orEmpty()
    }
}