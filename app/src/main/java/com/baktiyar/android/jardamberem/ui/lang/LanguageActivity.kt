package com.baktiyar.android.jardamberem.ui.lang

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.ConstantsJava
import com.baktiyar.android.jardamberem.ui.city.ChooseCityActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Const.Companion.HIDE_DRAWER
import com.baktiyar.android.jardamberem.utils.Const.Companion.KYRGYZ
import com.baktiyar.android.jardamberem.utils.Const.Companion.RUSSIAN
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.activity_choose_city.*
import org.jetbrains.anko.toast
import java.util.*

class LanguageActivity : BaseActivity() {

    var hideNav: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)

        hideNav = intent.getBooleanExtra(HIDE_DRAWER, true)
        setDrawerState(hideNav)
        title = getString(R.string.language)
        picker.visibility = View.VISIBLE
        val data: Array<String> = arrayOf(getString(R.string.ru_lang), getString(R.string.ky_lang))
        picker.minValue = 0
        picker.maxValue = 1

        if (Settings.getLanguage(applicationContext) == RUSSIAN) {
            picker.value = 0
        } else {
            picker.value = 1
        }

        picker.displayedValues = data
        picker.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS


        done.setOnClickListener {

            var restart = false
            val lang = if (picker.value == 0) RUSSIAN else KYRGYZ

            if (lang != Settings.getLanguage(baseContext)) {
                restart = true
            }


            val locale = Locale(if (lang == KYRGYZ) KYRGYZ else RUSSIAN)
            Locale.setDefault(locale)
            ConstantsJava.setLocale1(baseContext, locale)
            Settings.setLanguage(baseContext, lang)

            toast(getString(R.string.lang_choosen))
            if (!hideNav) {
                val toCity = Intent(this, ChooseCityActivity::class.java)
                toCity.putExtra(HIDE_DRAWER, false)
                startActivity(toCity)
                finish()
            }
            if (restart) {
                recreateScreen()
            }
        }

        }
    fun recreateScreen() {
        finish()
        overridePendingTransition( 0, 0);
        startActivity(intent)
        overridePendingTransition( 0, 0);
    }



    fun setDrawerState(hide: Boolean) {
        if (hide) {
            super.drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            super.toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED)
            super.toggle.isDrawerIndicatorEnabled = true
            super.toggle.syncState()

        } else {
            super.drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            super.toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            super.toggle.isDrawerIndicatorEnabled = false
            super.toggle.syncState()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}
