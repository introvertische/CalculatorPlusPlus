package info.introvertische.ip

import info.introvertische.ipcalculatorversionkotlin.IpCalculator.Converter
import info.introvertische.ipcalculatorversionkotlin.IpCalculator.IpCalculator
import info.introvertische.ipcalculatorversionkotlin.IpCalculator.IpValidator

class IpManager {
    private val ipValidator = IpValidator()
    private val converter = Converter()

    fun toStart() : IpCalculator {
        return IpCalculator(ipValidator, converter)
    }
}