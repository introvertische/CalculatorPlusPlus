package info.introvertische.calculator.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import info.introvertische.calculator.R
import info.introvertische.calculator.interfaces.ClickHandler

class BasicNumberPadFragment : Fragment(), View.OnClickListener {

    private lateinit var clickHandler: ClickHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_basic_number_pad, container, false)

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
            buttonClear
        )
        //endregion

        a.forEach {
            it.setOnClickListener(this)
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickHandler = context as ClickHandler
    }

    override fun onClick(v: View?) {
        clickHandler.sendData(v!!.id, false)
    }


}