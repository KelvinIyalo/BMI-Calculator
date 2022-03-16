package com.example.bmi

fun bmiResult(bmi: Double): String {
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