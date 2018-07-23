package com.baktiyar.android.jardamberem.ui.lang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.ConstantsJava
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Const.Companion.KYRGYZ
import com.baktiyar.android.jardamberem.utils.Const.Companion.RUSSIAN
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_choose_city.*
import org.jetbrains.anko.toast
import java.util.*

class LanguageActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)
        title = getString(R.string.language)
        picker.visibility = View.VISIBLE
        val data: Array<String> = arrayOf(getString(R.string.ky_lang), getString(R.string.ru_lang))
        picker.minValue = 0
        picker.maxValue = 1

        if (Settings.getLanguage(applicationContext) == RUSSIAN) {
            picker.value = 1
        } else {
            picker.value = 0
        }

        picker.displayedValues = data
        picker.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS

        /*picker.setOnValueChangedListener { picker, _, _ ->
            val lang = if (picker.value == 0) KYRGYZ else RUSSIAN
            Const.setLocale(this, Locale(lang))
        }*/
        done.setOnClickListener {
            var restart = false
            val lang = if (picker.value == 0) KYRGYZ else RUSSIAN

            if (lang != Settings.getLanguage(baseContext)) {
                restart = true
            }

/*            ConstantsJava.setLocale(baseContext, Locale(lang))
            Settings.setLanguage(baseContext, lang)
            */

            val locale = Locale(if (lang == KYRGYZ) KYRGYZ else RUSSIAN)
            Locale.setDefault(locale)
            ConstantsJava.setLocale1(baseContext, locale)
            Settings.setLanguage(baseContext, lang)

            toast(getString(R.string.lang_choosen))
            if (restart) {
                recreate()
            }
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}
