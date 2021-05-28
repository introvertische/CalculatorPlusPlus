package info.introvertische.calculator.activity

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import info.introvertische.calculator.R
import info.introvertische.ip.IpManager
import kotlinx.android.synthetic.main.activity_ip.*

class IPActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private var isEnterId = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip)

        switchNetworkIp.setOnCheckedChangeListener(this)
    }

    fun onClickCalculate(view: View) {
        val ipManager = IpManager().toStart()
        val firstPartIp = editInputFirstIp.text.toString()
        val secondPartIp = editInputSecondIp.text.toString()
        val thirdPartIp = editInputThirdIp.text.toString()
        val fourthPartIp = editInputFourthIp.text.toString()
        val ip = "$firstPartIp.$secondPartIp.$thirdPartIp.$fourthPartIp"
        val mask = spinnerMask.selectedItem.toString().split(" – ")

        if (isEnterId) {
            ipManager.create(ip, mask[1], editTextId.text.toString())
        } else {
            ipManager.create(ip, mask[1])
            textOutputId.text = ipManager.id
        }

        textInputDecNetworkClass.text = ipManager.getIpClass()
        textInputDecNetworkIpAddress.text = ipManager.getSubnetAddress(false)
        textInputDecSubnetNode.text = ipManager.getNumberOfNodes()
        textInputDecSubnet.text = ipManager.getNumberOfSubnet()
        val a = ipManager.calculateFirstIpAddress(false)
        textInputDecFirstIpAddress.text = a
        textInputDecLastIpAddress.text = ipManager.calculateLastIpAddress(false)
        textInputDecBroadcastAddress.text = ipManager.calculateBroadcastAddress(false)

        textInputBinIpAddress.text = ipManager.getIpAddress(true)
        textInputBinMask.text = ipManager.getMask(true)
        textInputBinNetworkIpAddress.text = ipManager.getSubnetAddress(true)
        textInputBinFirstIpAddress.text = ipManager.calculateFirstIpAddress(true)
        textInputBinLastIpAddress.text = ipManager.calculateLastIpAddress(true)
        textInputBinBroadcastAddress.text = ipManager.calculateBroadcastAddress(true)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            isEnterId = true
            //TODO: Реализовать смену цвета переключателя
            textOutputId.visibility = View.INVISIBLE
            editTextId.visibility = View.VISIBLE
        } else {
            isEnterId = false
            textOutputId.visibility = View.VISIBLE
            editTextId.visibility = View.INVISIBLE
        }
    }


}