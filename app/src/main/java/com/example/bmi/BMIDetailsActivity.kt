package com.example.bmi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.bmi.databinding.ActivityBmidetailsBinding
import java.io.File
import java.io.FileOutputStream
import java.util.*

class BMIDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityBmidetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmidetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val header = binding.bmiValue
        val user_name = binding.textviewStatus

        val value = intent.getStringExtra("bmi")
        val name = intent.getStringExtra("name")

        header.text = value?.toDouble().toString()
        user_name.text = "Hello ${name.toString()}, You are ${bmiResult(value!!.toDouble())}"
        binding.btnShare.setOnClickListener {

            screenShot()
        }

    }


    private fun bmiResult(bmi: Double): String {
        var ans = ""
        if (bmi < 18.5) {
            ans = "Underweight"
        } else if (bmi > 18.5 && bmi < 24.9) {
            ans = "Normal"
        } else if (bmi > 25 && bmi < 29.9) {
            ans = "Overweight"
        } else {
            ans = "Obese"
        }

        return ans
    }

    private fun screenShot() {

     val date = Date()

     val path = getExternalFilesDir(null)?.absolutePath +"/"+date +"jpg"
     var bitmap = Bitmap.createBitmap(binding.linearLayout.width, binding.linearLayout.height,Bitmap.Config.ARGB_8888)
     var canvas = Canvas(bitmap)
     binding.linearLayout.draw(canvas)
     val imageFile = File(path)
     val outPutStream = FileOutputStream(imageFile)
     bitmap.compress(Bitmap.CompressFormat.JPEG,100, outPutStream)
     outPutStream.flush()
     outPutStream.close()

   val URI = FileProvider.getUriForFile(applicationContext, "com.example.bmi.MainActivity.provider",imageFile)

      val intent = Intent()
      intent.action = Intent.ACTION_SEND
      intent.putExtra(Intent.EXTRA_TEXT,"Please see my BMI Result")
      intent.putExtra(Intent.EXTRA_STREAM,URI)
      intent.type = "text/plain"
      startActivity(intent)


    }




}