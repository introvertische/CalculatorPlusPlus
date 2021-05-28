package info.introvertische.ipcalculatorversionkotlin.IpCalculator

class Converter {

    private val binary = 2

    fun toBin(inputValue: Int) : String {
        return Integer.toBinaryString(inputValue)
    }

    fun toBin(inputValue: List<String>) : List<String> {
        val outputValue: ArrayList<String> = arrayListOf()
        for (it in inputValue) {
            outputValue.add(toBin(it.toInt()))
        }
        return outputValue
    }

    fun toDec(inputValue: String) : String {
        return inputValue.toInt(binary).toString()
    }

    fun toDec(inputValue: List<String>) : List<String> {
        val outputValue: ArrayList<String> = arrayListOf()
        for (it in inputValue) {
            outputValue.add(toDec(it))
        }
        return outputValue
    }
}