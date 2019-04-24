package edu.washington.jonl1138.tipcalc

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivitySpecial";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val input = findViewById<EditText>(R.id.input)
        val button = findViewById<Button>(R.id.button)

        input.addTextChangedListener (object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                button.setEnabled(false)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val text = p0.toString().trim()
                button.setEnabled(!text.isEmpty())

            }

        })
        input.setFilters(Array(100, {DecimalDigitsInputFilter(5, 2)}))
        button.setOnClickListener {
            Log.i(TAG, "reached here")
            Log.i(TAG, "" + input.text.javaClass.name)
            Log.i(TAG, "" + input.text.toString().javaClass.name)
            Log.i(TAG, "" + input.text.toString())
            val tipAmount: Double = String.format("%.2f",input.text.toString().toDouble() * 0.15).toDouble()
            val toast = Toast.makeText(this, "$" + tipAmount.toString(), Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class DecimalDigitsInputFilter(val digitsBeforeZero: Int, val digitsAfterZero: Int): InputFilter {
    private val mPattern: Pattern
    init {
        mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
    }

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val matcher: Matcher = mPattern.matcher(dest)
        if(!matcher.matches())
            return "";
        return null
    }
}
