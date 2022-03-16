package com.example.bmi

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.bmi.databinding.ActivityBmidetailsBinding
import com.google.android.gms.ads.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class BMIDetailsActivity : AppCompatActivity() {
    lateinit var mAdView: AdView
    lateinit var binding: ActivityBmidetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmidetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadAds()

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


    private fun screenShot() {

        val date = Date()

        val path = getExternalFilesDir(null)?.absolutePath + "/" + date + "jpg"
        var bitmap = Bitmap.createBitmap(
            binding.linearLayout.width,
            binding.linearLayout.height,
            Bitmap.Config.ARGB_8888
        )
        var canvas = Canvas(bitmap)
        binding.linearLayout.draw(canvas)
        val imageFile = File(path)
        val outPutStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outPutStream)
        outPutStream.flush()
        outPutStream.close()

        val URI =
            FileProvider.getUriForFile(this, "com.example.bmi.MainActivity.provider", imageFile)

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Please see my BMI Result")
        intent.putExtra(Intent.EXTRA_STREAM, URI)
        intent.type = "image/*"
        startActivity(intent)


    }

    fun loadAds() {
        MobileAds.initialize(this) {}

        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                Toast.makeText(this@BMIDetailsActivity,
                    "Adds was loaded successfully",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                Toast.makeText(this@BMIDetailsActivity,
                    "Adds Closed",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


}