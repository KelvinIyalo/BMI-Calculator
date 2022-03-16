package com.example.bmi

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bmi.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.shawnlin.numberpicker.NumberPicker

class MainActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BMI)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadInterAds()


        val editText = binding.etName
        val gender = binding.genderPicker


        val values = arrayOf("Male", "Female")
        gender.minValue = 0
        gender.maxValue = values.size - 1
        gender.displayedValues = values

        binding.btnCalculate.setOnClickListener {
            if (editText.text.toString() == ""){
                    editText.error = "TextField Can not be blank"

            }else{
            Toast.makeText(this,editText.text.toString(),Toast.LENGTH_LONG).show()
            showAds()

        }
        }


    }
    fun showAds(){
        val editText = binding.etName
        val hight = binding.hightPicker
        val weight = binding.wightPicker
        if (mInterstitialAd != null){

            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback (){
                override fun equals(other: Any?): Boolean {
                    return super.equals(other)
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {

                    super.onAdDismissedFullScreenContent()
                    val intent = Intent(this@MainActivity, BMIDetailsActivity::class.java)
                    intent.putExtra("name",editText.text.toString().trim())
                    intent.putExtra("bmi", (weight.value/((hight.value/100f)*(hight.value/100f))).toString())
                    startActivity(intent)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                }

            }
            mInterstitialAd?.show(this)

        }else{

            val intent = Intent(this@MainActivity, BMIDetailsActivity::class.java)
            intent.putExtra("name",editText.text.toString().trim())
            intent.putExtra("bmi", (weight.value/((hight.value/100f)*(hight.value/100f))).toString())
            startActivity(intent)

        }


    }

    private fun loadInterAds() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-7265395009083397/2488260482", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

}