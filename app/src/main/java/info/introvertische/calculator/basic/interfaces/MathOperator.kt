package interfaces

interface MathOperator {
    fun calculate(firstValue: String, secondValue: String = "") : String
}