package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bmi.databinding.ActivityMainBinding
import com.shawnlin.numberpicker.NumberPicker

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BMI)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val hight = binding.hightPicker
        val weight = binding.wightPicker
        val gender = binding.genderPicker
        val editText = binding.etName

        val values = arrayOf("Male", "Female")
        gender.minValue = 0
        gender.maxValue = values.size - 1
        gender.displayedValues = values

        binding.btnCalculate.setOnClickListener {
            if (editText.text.toString() == ""){
                    editText.error = "TextField Can not be blank"

            }else{
            Toast.makeText(this,editText.text.toString(),Toast.LENGTH_LONG).show()

            val intent = Intent(this, BMIDetailsActivity::class.java)
                intent.putExtra("name",editText.text.toString().trim())
            intent.putExtra("bmi", (weight.value/((hight.value/100f)*(hight.value/100f))).toString())
            startActivity(intent)
        }
        }


    }

}