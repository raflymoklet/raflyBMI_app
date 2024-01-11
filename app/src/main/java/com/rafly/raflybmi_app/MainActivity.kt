package com.rafly.raflybmi_app

import android.os.Bundle
import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.apk.ui.theme.APKTheme
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import com.rafly.raflybmi_app.R

class MainActivity : ComponentActivity() {

    private var gender: String = "Laki-laki"
    private var bmiResult: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val buttonReset = findViewById<Button>(R.id.buttonReset) // Tambahkan buttonReset
        val editTextHeight = findViewById<EditText>(R.id.editTextHeight)
        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextAddress = findViewById<EditText>(R.id.editTextAddress)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)

        if (savedInstanceState != null) {
            // Restore the previously calculated BMI result
            bmiResult = savedInstanceState.getString("bmiResult", "")
            textViewResult.text = bmiResult
        }

        buttonCalculate.setOnClickListener {
            calculateBMI(
                editTextHeight,
                editTextWeight,
                editTextName,
                editTextAddress,
                radioGroupGender,
                textViewResult
            )
        }

        buttonReset.setOnClickListener {
            resetFields(
                editTextHeight,
                editTextWeight,
                editTextName,
                editTextAddress,
                radioGroupGender,
                textViewResult
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save the calculated BMI result
        outState.putString("bmiResult", bmiResult)
        super.onSaveInstanceState(outState)
    }

    private fun calculateBMI(
        editTextHeight: EditText,
        editTextWeight: EditText,
        editTextName: EditText,
        editTextAddress: EditText,
        radioGroupGender: RadioGroup,
        textViewResult: TextView
    ) {
        val height = editTextHeight.text.toString().toDouble()
        val weight = editTextWeight.text.toString().toDouble()
        val name = editTextName.text.toString()
        val address = editTextAddress.text.toString()

        val selectedGenderId = radioGroupGender.checkedRadioButtonId

        gender = when (selectedGenderId) {
            R.id.radioButtonMale -> "Laki-laki"
            R.id.radioButtonFemale -> "Perempuan"
            else -> "Laki-laki"
        }

        val bmi = when (gender) {
            "Laki-laki" -> weight / ((height / 100) * (height / 100))
            "Perempuan" -> weight / ((height / 100) * (height / 100)) * 0.9
            else -> 0.0
        }

        val result = when {
            bmi < 18.5 -> "Berat badan kurang"
            bmi >= 18.5 && bmi < 24.9 -> "Berat badan normal"
            bmi >= 25 && bmi < 29.9 -> "Berat badan berlebih"
            else -> "Obesitas"
        }

        // Menampilkan nama, alamat, dan hasil BMI
        bmiResult = "Nama: $name\nAlamat: $address\nBMI:%.2f\n$result".format(bmi)
        textViewResult.text = bmiResult
    }

    private fun resetFields(
        editTextHeight: EditText,
        editTextWeight: EditText,
        editTextName: EditText,
        editTextAddress: EditText,
        radioGroupGender: RadioGroup,
        textViewResult: TextView
    ) {
        editTextHeight.text.clear()
        editTextWeight.text.clear()
        editTextName.text.clear()
        editTextAddress.text.clear()
        radioGroupGender.clearCheck()
        textViewResult.text = ""
        bmiResult = ""
    }
}