package info.introvertische.calculator.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import info.introvertische.calculator.R
import info.introvertische.calculator.fragments.BasicNumberPadFragment
import info.introvertische.calculator.fragments.EngineeringNumberPadFragment
import info.introvertische.calculator.fragments.HistoryFragment
import info.introvertische.calculator.interfaces.ClickHandler
import info.introvertische.clbyi.clbyi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonAdditions
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonClosingParenthesis
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonComma
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonDivisions
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonMultiplications
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberEight
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberFive
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberFour
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberNine
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberOne
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberSeven
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberSix
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberTree
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberTwo
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonNumberZero
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonOpeningParenthesis
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonPow
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonSqrt
import kotlinx.android.synthetic.main.fragment_basic_number_pad.buttonSubtractions
import kotlinx.android.synthetic.main.fragment_engineering_number_pad.*

class MainActivity : AppCompatActivity(), ClickHandler {

    private val mathLib = clbyi()

    private val basicNumberPadFragment = BasicNumberPadFragment()
    private val engineeringNumberPadFragment = EngineeringNumberPadFragment()

    private var answers = arrayListOf<String>()
    private var expressions = arrayListOf<String>()

    private  lateinit var lastFragment: Fragment
    private var isHistory = false
    private var isEngineeringPad = false
    private var instanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            inputWindow.showSoftInputOnFocus = false;
            R.layout.activity_main
        }

        instanceState = savedInstanceState
        setCursorToEnd()

        lastFragment = if (instanceState == null && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.numberPad, basicNumberPadFragment, "basic_pad")
                .commit()
            basicNumberPadFragment
        } else {
            replaceFragment(R.id.numberPad, engineeringNumberPadFragment)
            engineeringNumberPadFragment
        }
    }

    private fun setCursorToEnd() {
        inputWindow.setSelection(inputWindow.text.toString().length)
    }

    private fun createMathExpression(text: String) {
        inputWindow.append(text)
    }

    private fun deleteSymbol() {
        val text = inputWindow.text.toString()
        if (text.isNotEmpty())
            inputWindow.setText(text.substring(0, text.length - 1))
        setCursorToEnd()
    }

    private fun addElementToWindow(buttonText: String, isStart: Boolean = false, isBracket: Boolean = false) {
        val inputText = inputWindow.text.toString()
        val outputText = outputWindow.text.toString()
        if (inputText.isEmpty() && outputText.isNotEmpty()) {
            outputWindow.text = ""
            inputWindow.setText("$outputText$buttonText")
            setCursorToEnd()
        } else if (inputText.isNotEmpty() || isStart) {
            if (buttonText == inputText.lastOrNull().toString() && !isBracket)
                deleteSymbol()
            else
                createMathExpression(buttonText)
        }
    }

    private fun outputToScreen() {
        var outputText = ""
        if (inputWindow.text.toString().isNotEmpty())
            outputText = mathLib.toStart(
                    inputWindow.text
                            .toString()
                            .replace(getString(R.string.text_sign_subtractions), "-")
                            .replace(",", ".")
            )
        outputText = outputText
                .replace("-", getString(R.string.text_sign_subtractions))
                .replace(".", ",")

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (outputText == "f4e" || outputText == "f1b")
                outputWindow.text = getString(R.string.invalid_expression)
            else {
                outputWindow.text = outputText
            }
        } else {
            if (outputText == "f4e" || outputText == "f1b")
                inputWindow.setText(getString(R.string.invalid_expression))
            else
                inputWindow.setText(outputText)
        }
        setCursorToEnd()

        answers.add(outputText)
        expressions.add(inputWindow.text.toString())
    }

    override fun sendData(id: Int, isInv: Boolean) {
        when (id) {
            R.id.buttonNumberZero -> createMathExpression(buttonNumberZero.text.toString())
            R.id.buttonNumberOne -> createMathExpression(buttonNumberOne.text.toString())
            R.id.buttonNumberTwo -> createMathExpression(buttonNumberTwo.text.toString())
            R.id.buttonNumberTree -> createMathExpression(buttonNumberTree.text.toString())
            R.id.buttonNumberFour -> createMathExpression(buttonNumberFour.text.toString())
            R.id.buttonNumberFive -> createMathExpression(buttonNumberFive.text.toString())
            R.id.buttonNumberSix -> createMathExpression(buttonNumberSix.text.toString())
            R.id.buttonNumberSeven -> createMathExpression(buttonNumberSeven.text.toString())
            R.id.buttonNumberEight -> createMathExpression(buttonNumberEight.text.toString())
            R.id.buttonNumberNine -> createMathExpression(buttonNumberNine.text.toString())
            R.id.buttonClear -> {
                inputWindow.setText("")
                outputWindow.text = ""
            }
            R.id.buttonOpeningParenthesis -> addElementToWindow(buttonOpeningParenthesis.text.toString(), true, isBracket = true)
            R.id.buttonClosingParenthesis -> addElementToWindow(buttonClosingParenthesis.text.toString(), isBracket = true)
            R.id.buttonDelete-> deleteSymbol()
            R.id.buttonPercent -> ""
            R.id.buttonSqrt -> addElementToWindow(buttonSqrt.text.toString(), true)
            R.id.buttonPow -> addElementToWindow(buttonPow.text.toString())
            R.id.buttonDivisions -> addElementToWindow(buttonDivisions.text.toString())
            R.id.buttonMultiplications -> addElementToWindow(buttonMultiplications.text.toString())
            R.id.buttonSubtractions -> addElementToWindow(buttonSubtractions.text.toString(), true)
            R.id.buttonAdditions -> addElementToWindow(buttonAdditions.text.toString())
            R.id.buttonComma -> addElementToWindow(buttonComma.text.toString())
            R.id.buttonPi -> createMathExpression(buttonPi.text.toString())
            R.id.buttonE -> createMathExpression(buttonE.text.toString())
            R.id.buttonLog -> addElementToWindow(buttonLog.text.toString() + "(", true)
            R.id.buttonLn -> addElementToWindow(buttonLn.text.toString() + "(", true)
            R.id.buttonCbrt -> addElementToWindow(buttonCbrt.text.toString(), true)
            R.id.buttonFactorial -> addElementToWindow(buttonFactorial.text.toString())
            R.id.buttonMod -> addElementToWindow(buttonMod.text.toString())
            R.id.buttonSin -> addElementToWindow(buttonSin.text.toString() + "(", true)
            R.id.buttonCos -> addElementToWindow(buttonCos.text.toString() + "(", true)
            R.id.buttonTg -> addElementToWindow(buttonTg.text.toString() + "(", true)
            R.id.buttonModule -> addElementToWindow(getString(R.string.text_sign_module_two) + "(", true)
            R.id.buttonInv -> null
            R.id.buttonEqual -> outputToScreen()
            else -> addElementToWindow("null")
        }
    }

    private fun replaceFragment(id: Int, fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(id, fragment)
                .addToBackStack(null)
                .commit()
    }

    fun onClickHistory(view: View) {

        val bundle = Bundle()
        bundle.putStringArrayList("answer", answers)
        bundle.putStringArrayList("expression", expressions)

        val historyPad = HistoryFragment()

        historyPad.arguments = bundle

        isHistory = !isHistory
        if (instanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.numberPad, historyPad, "history_pad")
                    .commit()
        }
        if (isHistory) {
            replaceFragment(R.id.numberPad, historyPad)
        } else
            replaceFragment(R.id.numberPad, lastFragment)
    }

    fun toChangeLayout(view: View) {
        val engineeringNumberPad = EngineeringNumberPadFragment()
        lastFragment = engineeringNumberPad
        isEngineeringPad = !isEngineeringPad
        if (instanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.numberPad, engineeringNumberPad, "engineering_pad")
                    .commit()
        }
        if (isEngineeringPad)
            replaceFragment(R.id.numberPad, engineeringNumberPad)
        else
            replaceFragment(R.id.numberPad, basicNumberPadFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun onClickAbout(item: MenuItem?) {
        val builder = AlertDialog.Builder(this@MainActivity, R.style.CustomDialog)
        val view = layoutInflater
                .inflate(R.layout.dialog_about, null) as ConstraintLayout
        builder
                .setTitle(getString(R.string.app_name))
                .setView(view)
                .setIcon(R.drawable.cpp_icon)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.about_close),
                        DialogInterface.OnClickListener { dialog: DialogInterface, id: Int -> dialog.cancel() })
        val alert = builder.create()
        alert.show()
    }
}