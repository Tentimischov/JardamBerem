package com.baktiyar.android.jardamberem.utils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.lang.LanguageActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.HIDE_DRAWER
import kotlinx.android.synthetic.main.activity_agreement.*

class AgreementActivity : AppCompatActivity() {

    var isAgreed: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agreement)


        val intent = Intent(this, MainActivity::class.java)


        isAgreed = Settings.getAgreement(this)


        if (isAgreed as Boolean) {
            startActivity(intent)
        }

        agreementRadioButton.setOnClickListener {
            isAgreed = true

            Settings.setAgreement(this, isAgreed)
            val toLanguage = Intent(this, LanguageActivity::class.java)
            toLanguage.putExtra(HIDE_DRAWER, false)
            startActivity(toLanguage)
            finish()
        }


    }


}
