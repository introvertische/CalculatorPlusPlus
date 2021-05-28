package info.introvertische.ipcalculatorversionkotlin.IpCalculator

import info.introvertische.ipcalculatorversionkotlin.interfaces.Validator

class IpValidator : Validator {
    override fun validation(inputValue: String): Boolean {
        val pattern = "\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|\$)){4}\\b".toRegex()
        return pattern.containsMatchIn(inputValue)
    }
}