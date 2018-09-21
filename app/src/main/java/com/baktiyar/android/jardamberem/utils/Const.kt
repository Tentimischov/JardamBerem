package com.baktiyar.android.jardamberem.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import java.util.*

class Const {
    companion object {
        val CAMERA = 1
        val GALLERY = 2
        val CACHE_PATH = "cache"
        val DIR_PRODUCTS = "products"
        val IMAGE_URL_EXTRA = "IMAGE_URL_EXTRA"
        val VIEW_INFO_EXTRA = "VIEW_INFO_EXTRA"
        val PROPNAME_SCREENLOCATION_LEFT = "PROPNAME_SCREENLOCATION_LEFT"
        val PROPNAME_SCREENLOCATION_TOP = "PROPNAME_SCREENLOCATION_TOP"
        val PROPNAME_WIDTH = "PROPNAME_WIDTH"
        val PROPNAME_HEIGHT = "PROPNAME_HEIGHT"
        val GOODS = "DETAILED GOODS"
        val RUSSIAN = "ru"
        val KYRGYZ = "ky"
        val URGENTS = "URGENTS"
        val ACTION_URGENT = "ACTION_URGENT"
        val ACTION_DETAILED = "ACTION_DETAILED"
        const val CATEGORY_ID: String = "category_id"
        const val CATEGORY_NAME: String = "CATEGORY_NAME"
        const val ACTIVITY_ID: String = "ACTIVITY_ID"
        const val INFO_DETAILED: String = "INFO_DETAILED"
        const val DETAILED_ACTIVITY: String = "DETAILED_ACTIVITY"
        const val INFO_ACTIVITY: String = "INFO_ACTIVITY"
        const val HIDE_DRAWER: String = "HIDE_DRAWER"
        val ALL_PHOTO_URLS: String = "ALL_PHOTO_URLS"
        val INDEX: String = "INDEX"
        fun hideKeyboard(activity: Activity) {
            val view: View? = activity.window.currentFocus
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
        }

        fun setVisiblityMenuItem(menu: Menu, exception: MenuItem, visibile: Boolean) {
            for (i in 0 until menu.size()) {
                val it: MenuItem = menu.getItem(i)
                if (it != exception) {
                    it.isVisible = visibile
                }
            }
        }

        fun RecreateActivity(activity: AppCompatActivity) {
            activity.recreate()
        }

        class SpinnerActivity(var context: Context, var appCompatActivity: MainActivity) : Activity(), AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (position != Settings.getSpinnerItemPosition(context)) {
                    Settings.setSpinnerItemPosition(context, position)
                    RecreateActivity(appCompatActivity)
                }
            }

        }

        fun setLocale(context: Context, locale: Locale) {
            val config = context.resources.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                config.setLocale(locale)
            else
                config.locale = locale
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

        fun showDeleteProductDialog(context: Context, title: String): Boolean {
            var ok: Boolean = false
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        ok = true
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {

                    }
                }
            }
            val builder = AlertDialog.Builder(context)
            builder.setMessage(context.getString(R.string.check_to_delete, title))
                    .setPositiveButton(context.getString(R.string.yes), dialogClickListener)
                    .setNegativeButton(context.getString(R.string.no), dialogClickListener).show()

            return ok
        }

    }

}