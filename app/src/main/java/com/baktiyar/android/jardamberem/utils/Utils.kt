package com.baktiyar.android.jardamberem.utils

import android.util.Log
import android.widget.Toast
import com.baktiyar.android.jardamberem.StartApplication

class Utils {
    companion object {
        fun e(s: Any) {
            Log.e("___________", s.toString())
        }

    }
}

fun e(s: Any) {
    Log.e("___________", s.toString())
}

fun String.toToast() {
    Toast.makeText(StartApplication.INSTANCE, this, Toast.LENGTH_SHORT).show()
}