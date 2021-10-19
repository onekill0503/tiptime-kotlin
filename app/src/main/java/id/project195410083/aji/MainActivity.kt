package id.project195410083.aji

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cacl_btn: Button = findViewById(R.id.calculate_btn)
        cacl_btn.setOnClickListener { calculateTip() }
        val costOfServiceEditText: TextInputLayout = findViewById(R.id.cost_of_service)
        costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }
    private fun calculateTip(){
        // Get the decimal value from the cost of service EditText field
        var inptbox: TextInputEditText = findViewById(R.id.cost_of_service_edit_text)
        val stringInTextField = inptbox.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null || cost == 0.0) {
            showTip(0.0)
            return
        }

        // Get the tip percentage based on which radio button is selected
        val rg: RadioGroup = findViewById(R.id.radioGroup)
        val tipPercentage = when (rg.checkedRadioButtonId) {
            R.id.percent20 -> 0.20
            R.id.percent18 -> 0.18
            else -> 0.15
        }

        // Calculate the tip
        var tip = tipPercentage * cost

        // If the switch for rounding up the tip toggled on (isChecked is true), then round up the
        // tip. Otherwise do not change the tip value.
        val switch: com.google.android.material.switchmaterial.SwitchMaterial = findViewById(R.id.round_up_switch)
        val roundUp = switch.isChecked
        if (roundUp) {
            // Take the ceiling of the current tip, which rounds up to the next integer, and store
            // the new value in the tip variable.
            tip = kotlin.math.ceil(tip)
        }

        // Display the formatted tip value onscreen
        showTip(tip)
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
    private fun showTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val res: TextView = findViewById(R.id.tipresult)
        res.text = "Tip Amount : " + formattedTip
    }
}