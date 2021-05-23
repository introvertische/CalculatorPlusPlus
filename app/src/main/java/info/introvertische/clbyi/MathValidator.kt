package info.introvertische.clbyi

import info.introvertische.clbyi.interfaces.Validator

class MathValidator : Validator {
    override fun validation(inputValue: String) : Boolean {
        val charValue = inputValue.toCharArray()
        var startCount = 0
        var endCount = 0
        for (i in charValue.indices) {
            if (charValue[i] == '(')
                startCount++
            if (charValue[i] == ')')
                endCount++
        }
        return startCount == endCount
    }
}