package info.introvertische.calculator.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import info.introvertische.calculator.R
import info.introvertische.calculator.interfaces.ClickHandler

class EngineeringNumberPadFragment : Fragment(), View.OnClickListener {

    private lateinit var hand: ClickHandler

    private lateinit var buttonInv: TextView
    private lateinit var buttonSin: TextView
    private lateinit var buttonCos: TextView
    private lateinit var buttonTg: TextView

    private var isInv = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_engineering_number_pad, container, false)

        //region #Create buttons
        val buttonZero: TextView = rootView.findViewById(R.id.buttonNumberZero)
        val buttonOne: TextView = rootView.findViewById(R.id.buttonNumberOne)
        val buttonTwo: TextView = rootView.findViewById(R.id.buttonNumberTwo)
        val buttonTree: TextView = rootView.findViewById(R.id.buttonNumberTree)
        val buttonFour: TextView = rootView.findViewById(R.id.buttonNumberFour)
        val buttonFive: TextView = rootView.findViewById(R.id.buttonNumberFive)
        val buttonSix: TextView = rootView.findViewById(R.id.buttonNumberSix)
        val buttonSeven: TextView = rootView.findViewById(R.id.buttonNumberSeven)
        val buttonEight: TextView = rootView.findViewById(R.id.buttonNumberEight)
        val buttonNine: TextView = rootView.findViewById(R.id.buttonNumberNine)
        val buttonComma: TextView = rootView.findViewById(R.id.buttonComma)
        val buttonEqual: TextView = rootView.findViewById(R.id.buttonEqual)
        val buttonAdditions: TextView = rootView.findViewById(R.id.buttonAdditions)
        val buttonSubtractions: TextView = rootView.findViewById(R.id.buttonSubtractions)
        val buttonMultiplications: TextView = rootView.findViewById(R.id.buttonMultiplications)
        val buttonDivisions: TextView = rootView.findViewById(R.id.buttonDivisions)
        val buttonPow: TextView = rootView.findViewById(R.id.buttonPow)
        val buttonSqrt: TextView = rootView.findViewById(R.id.buttonSqrt)
        val buttonOpeningParenthesis: TextView = rootView.findViewById(R.id.buttonOpeningParenthesis)
        val buttonClosingParenthesis: TextView = rootView.findViewById(R.id.buttonClosingParenthesis)
        val buttonDelete: TextView = rootView.findViewById(R.id.buttonDelete)
        val buttonPercent: TextView = rootView.findViewById(R.id.buttonPercent)
        val buttonClear: TextView = rootView.findViewById(R.id.buttonClear)
        buttonInv = rootView.findViewById(R.id.buttonInv)
        buttonSin = rootView.findViewById(R.id.buttonSin)
        buttonCos = rootView.findViewById(R.id.buttonCos)
        buttonTg = rootView.findViewById(R.id.buttonTg)
        val buttonMod: TextView = rootView.findViewById(R.id.buttonMod)
        val buttonFactorial: TextView = rootView.findViewById(R.id.buttonFactorial)
        val buttonCbrt: TextView = rootView.findViewById(R.id.buttonCbrt)
        val buttonLn: TextView = rootView.findViewById(R.id.buttonLn)
        val buttonLog: TextView = rootView.findViewById(R.id.buttonLog)
        val buttonE: TextView = rootView.findViewById(R.id.buttonE)
        val buttonPi: TextView = rootView.findViewById(R.id.buttonPi)
        val buttonModule: TextView = rootView.findViewById(R.id.buttonModule)
        //endregion

        //region #Battons array
        val a: Array<TextView> = arrayOf(
                buttonZero,
                buttonOne,
                buttonTwo,
                buttonTree,
                buttonFour,
                buttonFive,
                buttonSix,
                buttonSeven,
                buttonEight,
                buttonNine,
                buttonComma,
                buttonEqual,
                buttonAdditions,
                buttonSubtractions,
                buttonMultiplications,
                buttonDivisions,
                buttonPow,
                buttonSqrt,
                buttonOpeningParenthesis,
                buttonClosingParenthesis,
                buttonDelete,
                buttonPercent,
                buttonClear,
                buttonInv,
                buttonSin,
                buttonCos,
                buttonTg,
                buttonMod,
                buttonFactorial,
                buttonCbrt,
                buttonLn,
                buttonLog,
                buttonE,
                buttonPi,
                buttonModule
        )
        //endregion

        a.forEach {
            it.setOnClickListener(this)
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        hand = context as ClickHandler
    }

    private fun invElement() {
        isInv = !isInv
        if (isInv) {
            buttonInv.setTextColor(resources.getColor(R.color.orange_500))
            buttonSin.text = getString(R.string.text_sign_arcsin)
            buttonCos.text = getString(R.string.text_sign_arccos)
            buttonTg.text = getString(R.string.text_sign_arctg)
        } else {
            buttonInv.setTextColor(resources.getColor(R.color.white))
            buttonSin.text = getString(R.string.text_sign_sin)
            buttonCos.text = getString(R.string.text_sign_cos)
            buttonTg.text = getString(R.string.text_sign_tg)
        }
    }

    override fun onClick(v: View?) {

        if (v!!.id == R.id.buttonInv) {
            invElement()
            hand.sendData(v!!.id, true)
        } else {
            hand.sendData(v!!.id, false)
        }
    }
}