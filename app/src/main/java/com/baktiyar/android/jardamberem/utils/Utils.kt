package com.baktiyar.android.jardamberem.utils

import android.util.Log
import android.widget.Toast
import com.baktiyar.android.jardamberem.StartApplication

fun String.toToast() {
    Toast.makeText(StartApplication.INSTANCE, this, Toast.LENGTH_SHORT).show()
}

fun String.e() {
    Log.e("_____________", this)
}