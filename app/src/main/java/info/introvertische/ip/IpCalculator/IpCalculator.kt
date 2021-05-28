package info.introvertische.ipcalculatorversionkotlin.IpCalculator

import java.lang.StringBuilder
import kotlin.math.pow

class IpCalculator(validator: IpValidator, converter: Converter) {

    private val LEN_ELEMETS = 8

    private var validator: IpValidator = validator
    private var converter: Converter = converter

    private lateinit var ip: List<String>
    private lateinit var mask: List<String>
    var id: String = ""
        get() = field
        private set
    private lateinit var subnetAddress: List<String>

    //Entry point
    fun create(ip: String, mask: String, id: String = "") {
        this.ip = if (validator.validation(ip))
                        checkForElementSize(converter.toBin(ip.split(".")))
                    else
                        checkForElementSize(converter.toBin("0.0.0.0".split(".")))
        this.mask = checkForElementSize(converter.toBin(mask.split(".")))
        definingIdAndSubnetAddress()

        if (id.isNotEmpty())
            this.id = id
    }

    private fun repeatElements(count: Int, with: String): String {
        return List(count) { with }.joinToString("")
    }

    private fun addElements(element: String, valueElement: String, count: Int, addToEnd: Boolean) : String {
        val repeatElement = repeatElements(count - element.length, valueElement)
        return if (addToEnd) element + repeatElement else repeatElement + element
    }

    private fun checkForElementSize(value: List<String>) : List<String> {
        val newValue = arrayListOf<String>()
        for (it in value) {
            var element = ""
            if (it.length < LEN_ELEMETS)
                element = addElements(it, "0", LEN_ELEMETS, false)
            else
                element = it
            newValue.add(element)
        }
        return newValue
    }

    private fun definingIdAndSubnetAddress() {
        val binId = StringBuilder()
        val binSubnetAddress: ArrayList<String> = arrayListOf()
        var flagId = true
        val zeroElement = "0"
        for (i in ip.indices) {
            if (converter.toDec(mask[i]).toInt() != 255 && flagId) {
                flagId = false
                val locIp = ip[i].toCharArray()
                val locMask = mask[i].toCharArray()
                for (j in locMask.indices) {
                    if (locMask[0] == '0'){
                        binId.append(zeroElement)
                        break
                    } else if (locMask[j] == '1') {
                        binId.append(locIp[j])
                    } else {
                        break
                    }
                }
                binSubnetAddress.add(addElements(binId.toString(), zeroElement, LEN_ELEMETS, true))
            } else if (!flagId){
                binSubnetAddress.add(repeatElements(LEN_ELEMETS, zeroElement))
            } else {
                binSubnetAddress.add(ip[i])
            }
        }
        id = binId.toString()
        subnetAddress = binSubnetAddress
    }

    fun getIpClass(): String {
        return when (ip[0].toInt(2)) {
            in 1..126 -> "A"
            in 127..191 -> "B"
            in 192..223 -> "C"
            in 224..239 -> "D"
            in 240..247 -> "E"
            else -> "Unable to determine the IP address class!"
        }
    }

    private fun calculatingQuantity(value: Char): Int {
        var count = 0;
        mask.forEach { it ->
            if (converter.toDec(it).toInt() != 255)
                it.forEach { iw ->
                    if (iw == value ) {
                        count++
                    }
                }
        }
        return count
    }

    fun getNumberOfNodes(): String {
        return (2.0.pow(calculatingQuantity('0')) - 2).toInt().toString()
    }

    fun getNumberOfSubnet(): String {
        return (2.0.pow(calculatingQuantity('1'))).toInt().toString()
    }

    private fun calculateIpAddressValues(_value: String): List<String> {
        var binIpValue: ArrayList<String> = arrayListOf()
        var firstEntry = true
        val elementSize = mask.size - 1;
        val zeroElement = "0"

        var value = ""
        var inversValue = ""
        if (_value == "first") {
            value = "1"
            inversValue = "0"
        } else if (_value == "last") {
            value = "0"
            inversValue = "1"
        } else if (_value == "all") {
            value = "1"
            inversValue = "1"
        }

        val id = addElements(id, zeroElement, calculatingQuantity('1'), false)

        for (i in mask.indices) {
            var binOctet = ""
            if (converter.toDec(mask[i]).toInt() != 255){
                if (firstEntry && i != elementSize) {
                    firstEntry = false
                    // Сreating an id with all zeros
                    binOctet = addElements(id, inversValue, LEN_ELEMETS, true)
                } else if (firstEntry && i == elementSize) {
                    firstEntry = false
                    binOctet = addElements(id, inversValue, LEN_ELEMETS - 1, true) + value
                } else if (!firstEntry && i != elementSize) {
                    binOctet = repeatElements(LEN_ELEMETS, inversValue)
                } else if (!firstEntry && i == elementSize) {
                    binOctet =repeatElements(LEN_ELEMETS - 1, inversValue) + value
                }
            } else {
                binOctet = ip[i]
            }
            binIpValue.add(binOctet)
        }
        return binIpValue
    }

    private fun calculateIpAddress(binaryAddress: Boolean, key: String): String {
        val binIpValue = calculateIpAddressValues(key)
        return selectFormat(binIpValue, binaryAddress)
    }

    fun calculateFirstIpAddress(binaryAddress: Boolean): String {
        return calculateIpAddress(binaryAddress, "first")
    }

    fun calculateLastIpAddress(binaryAddress: Boolean): String {
        return calculateIpAddress(binaryAddress, "last")
    }

    fun calculateBroadcastAddress(binaryAddress: Boolean): String {
        return calculateIpAddress(binaryAddress, "all")
    }

    private fun selectFormat(value: List<String>, isBinary: Boolean) : String {
        return if (!isBinary) converter.toDec(value).joinToString(".")
                else value.joinToString(".")
    }

    fun getIpAddress(isBinary: Boolean) : String {
        return selectFormat(ip, isBinary)
    }

    fun getMask(isBinary: Boolean) : String {
        return selectFormat(mask, isBinary)
    }

    fun getSubnetAddress(isBinary: Boolean) : String {
        return selectFormat(subnetAddress, isBinary)
    }
}