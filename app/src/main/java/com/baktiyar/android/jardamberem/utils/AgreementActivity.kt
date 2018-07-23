package com.baktiyar.android.jardamberem.utils

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_agreement.*

class AgreementActivity : AppCompatActivity() {

    val PREFS_FILENAME = "com.baktiyar.android.jardamberem.ui"
    val AGREEMENT_CHEEKING = "agreement_checking"
    var isAgreed: Boolean? = false
    var prefs: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)


        val intent = Intent(this, MainActivity::class.java)


        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        isAgreed = prefs!!.getBoolean(AGREEMENT_CHEEKING, false)


        if (isAgreed as Boolean) {
            startActivity(intent)
        }

        agreementRadioButton.setOnClickListener {
            isAgreed = true
            val editor = prefs!!.edit()
            editor.putBoolean(AGREEMENT_CHEEKING, isAgreed!!)
            editor.apply()
            startActivity(intent)
        }


    }


}
