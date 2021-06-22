package info.introvertische.calculator.basic.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import info.introvertische.calculator.R
import info.introvertische.calculator.basic.EvaluateExpression
import info.introvertische.calculator.basic.fragments.BasicNumberPadFragment
import info.introvertische.calculator.basic.fragments.EngineeringNumberPadFragment
import info.introvertische.calculator.basic.fragments.HistoryFragment
import info.introvertische.calculator.basic.interfaces.ClickDeleteHistory
import info.introvertische.calculator.basic.interfaces.ClickHandler
import info.introvertische.calculator.basic.interfaces.ClickListItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_about.*
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
import java.lang.StringBuilder
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), ClickHandler, ClickDeleteHistory, ClickListItem {

    private val mathLib = EvaluateExpression()

    private val basicNumberPadFragment = BasicNumberPadFragment()
    private val engineeringNumberPadFragment = EngineeringNumberPadFragment()

    private var isFirstLaunch = true
    private lateinit var characters: List<String>
    private var answers: ArrayList<String> = arrayListOf()
    private var expressions: ArrayList<String> = arrayListOf()
    private var historyMap: LinkedHashMap<String, String> = linkedMapOf()

    private  lateinit var lastFragment: Fragment
    private var isHistory = false
    private var isEngineeringPad = false
    private var isNightMode = true
    private var instanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instanceState = savedInstanceState

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            inputWindow.showSoftInputOnFocus = false;
            R.layout.activity_main
        }

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

        characters = arrayListOf(
            getString(R.string.text_sign_additions),
            getString(R.string.text_sign_multiplications),
            getString(R.string.text_sign_divisions),
            getString(R.string.text_sign_percent),
            getString(R.string.text_sign_pow)
        )

        inputWindow.addTextChangedListener(inputWatcher)
        setCursorPosition()
    }

    private val inputWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            outputWindow.text = ""
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            outputWindow.text = ""
        }

        override fun afterTextChanged(s: Editable?) {
            val result = mathLibCalculate()
            if (result == "FAE") {
                outputWindow.text = ""
            } else {
                outputWindow.text = result
            }
        }

    }

    //TODO Implement the transition from two lists to one map
    override fun onStart() {
        super.onStart()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        answers = ArrayList(sharedPref.getString("answer", "")?.split(";"))
        expressions = ArrayList(sharedPref.getString("expressions", "")?.split(";"))
        for (i in 0 until answers.size) {
            historyMap[expressions[i]] = answers[i]
        }
    }

    private fun setCursorPosition(position: Int = -1) {
        if (position == -1)
            inputWindow.setSelection(inputWindow.text.toString().length)
        else
            inputWindow.setSelection(position)
    }

    private fun createMathExpression(text: String) {
        if (inputWindow.selectionStart == inputWindow.text.toString().length)
            inputWindow.append(text)
        else {
            val cursorPosition = inputWindow.selectionStart
            val inputText = StringBuilder().append(inputWindow.text.toString())
            inputText.insert(cursorPosition, text)
            inputWindow.setText(inputText.toString())
            setCursorPosition(cursorPosition+1)
        }
    }

    private fun deleteSymbol() {
        val text = inputWindow.text.toString()
        val cursorPosition  = inputWindow.selectionStart
        if (text.isNotEmpty() && cursorPosition != 0) {
            val start = text.substring(0, cursorPosition-1)
            val end = text.substring(cursorPosition, text.length)
            inputWindow.setText("$start$end")
            setCursorPosition(cursorPosition - 1)
        }

    }

    private fun addElementToWindow(buttonText: String, isStart: Boolean = false, isBracket: Boolean = false) {
        val inputText = inputWindow.text.toString()
        val outputText = outputWindow.text.toString()
        if (inputText.isEmpty() && outputText.isNotEmpty()) {
            outputWindow.text = ""
            inputWindow.setText("$outputText$buttonText")
            setCursorPosition()
        } else if (inputText.isNotEmpty() || isStart) {
            val cursorPosition = inputWindow.selectionStart
            var start  = inputText.substring(0, cursorPosition)
            val end = inputText.substring(cursorPosition, inputText.length)
            val last = start.lastOrNull().toString()
            if (characters.contains(last)){
                if (buttonText != last && characters.contains(buttonText)){
                    deleteSymbol()
                    start = start.substring(0, start.length-1)
                    inputWindow.setText("$start$buttonText$end")
                    setCursorPosition(cursorPosition)
                } else {
                    createMathExpression(buttonText)
                }
            } else {
                createMathExpression(buttonText)
            }
        }
    }

    private fun mathLibCalculate() : String {
        var outputText = mathLib.start(
            inputWindow.text
                .toString()
                .replace(getString(R.string.text_sign_subtractions), "-")
                .replace(",", ".")
        )
        outputText = outputText
            .replace("-", getString(R.string.text_sign_subtractions))
            .replace(".", ",")
        return outputText
    }

    private fun outputToScreen() {
        if (
            !isFirstLaunch &&
            inputWindow.text.toString() == expressions[expressions.size-1] &&
            outputWindow.text.isNotEmpty()
        ) {
            inputWindow.setText(outputWindow.text)
            outputWindow.text = ""
        } else {
            isFirstLaunch = false
            val inputText = inputWindow.text.toString()
            var outputText = ""

            if (inputText.isNotEmpty()) {
                outputText = if (outputWindow.text.isNotEmpty()) {
                    outputWindow.text.toString();
                } else {
                    mathLibCalculate()
                }
            }

            if (outputText.indexOf("FAE") != -1)
                    inputWindow.setText(getString(R.string.invalid_expression))
                else
                    inputWindow.setText(outputText)


//            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//                if (outputText.indexOf("FAE") != -1)
//                    outputWindow.text = getString(R.string.invalid_expression)
//                else
//                    outputWindow.text = outputText
//            } else {
//                if (outputText.indexOf("FAE") != -1)
//                    inputWindow.setText(getString(R.string.invalid_expression))
//                else
//                    inputWindow.setText(outputText)
//            }

//            if (inputWindow.text.toString().isNotEmpty() && outputWindow.text.toString().isNotEmpty()) {
//
//            }
            historyMap[inputText] = outputText
            outputWindow.text = ""

        }
        saveHistory()
        setCursorPosition()
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
        val historyPad = HistoryFragment()
        val transaction = supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)

        bundle.putStringArrayList("answer", answers)
        bundle.putStringArrayList("expression", expressions)
        historyPad.arguments = bundle

        isHistory = !isHistory

        if (instanceState == null) {
            transaction.add(R.id.numberPad, historyPad, "history_pad")
        }

        if (isHistory) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.replace(R.id.numberPad, historyPad)
        } else{
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            transaction.replace(R.id.numberPad, lastFragment)
        }
        transaction.commit()
    }

    //TODO Fix non-saving history
    private fun saveHistory() {
        val answer = historyMap.keys.joinToString(";")
        val expressions = historyMap.values.joinToString(";")

        val sharedPref= this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("answer", answer)
            putString("expressions", expressions)
            apply()
        }
    }

    override fun deleteHistory(isDelete: Boolean) {
        if (isDelete) {
            answers = arrayListOf()
            expressions = arrayListOf()
            saveHistory()
            replaceFragment(R.id.numberPad, lastFragment)
            isFirstLaunch = true
        }
    }

    fun toChangeLayout(view: View) {
        val engineeringNumberPad = EngineeringNumberPadFragment()
        isEngineeringPad = !isEngineeringPad
        if (instanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.numberPad, engineeringNumberPad, "engineering_pad")
                    .commit()
        }
        if (isEngineeringPad) {
            replaceFragment(R.id.numberPad, engineeringNumberPad)
            lastFragment = engineeringNumberPad
        }
        else {
            replaceFragment(R.id.numberPad, basicNumberPadFragment)
            lastFragment = basicNumberPadFragment
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun onClickAbout(item: MenuItem?) {
        val builder = AlertDialog.Builder(this@MainActivity, R.style.TemplDialog)
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

//    fun switchTheme (item: MenuItem?) {
//        isNightMode = !isNightMode
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//            if (isNightMode) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            }
//        } else {
//            //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            //themeSystem.visibility = View.GONE
//        }
//    }
//
//    private fun setActivityTheme(themeMode: Int) {
//        AppCompatDelegate.setDefaultNightMode(themeMode)
//    }

    override fun listItemPosition(position: Int) {
        inputWindow.setText(expressions[position])
        outputWindow.text = ""
        setCursorPosition()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        super.onBackPressed()
        System.runFinalizersOnExit(true);
        exitProcess(0);
    }
}