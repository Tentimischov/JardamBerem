@file:Suppress("DEPRECATION")

package com.baktiyar.android.jardamberem.ui

import android.app.ActivityManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.baktiyar.android.jardamberem.utils.MyContextWrapper
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.action.ActionActivity
import com.baktiyar.android.jardamberem.ui.appInfo.AppInfoActivity
import com.baktiyar.android.jardamberem.ui.city.ChooseCityActivity
import com.baktiyar.android.jardamberem.ui.feedback.FeedbackActivity
import com.baktiyar.android.jardamberem.ui.forum.ForumActivity
import com.baktiyar.android.jardamberem.ui.info.InfoActivity
import com.baktiyar.android.jardamberem.ui.lang.LanguageActivity
import com.baktiyar.android.jardamberem.ui.main.MainActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.hideKeyboard
import com.baktiyar.android.jardamberem.utils.Settings
import java.util.*


open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{


    private var mProgressBar: ProgressDialog? = null
    lateinit var rel: FrameLayout
    lateinit var toolbar: Toolbar
    lateinit var drawer_layout: DrawerLayout
    lateinit var nav_view: NavigationView
    lateinit var toggle: ActionBarDrawerToggle

    override fun setContentView(layoutResID: Int) {
        ConstantsJava.setLocale1(baseContext, Locale(Settings.getLanguage(baseContext)))
        super.setContentView(layoutResID)
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        rel = findViewById(R.id.content_frame)
        toolbar = findViewById(R.id.toolbar)

        init()
    }


    private fun init() {
        setSupportActionBar(toolbar)

        val id = intent.getIntExtra("id", R.id.main_menu)


        toggle = object : ActionBarDrawerToggle(this,
                drawer_layout, toolbar, R.string.app_name, R.string.app_name) {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                hideKeyboard(this@BaseActivity)
            }
        }


        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(id)
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        var id: Int = item.itemId
        when (id) {
            R.id.main_menu -> goToActivity(MainActivity::class.java, id)
            R.id.action_activity -> goToActivity(ActionActivity::class.java, id)
            R.id.info -> goToActivity(InfoActivity::class.java, id)
            R.id.forum -> goToActivity(ForumActivity::class.java, id)
            R.id.lang -> goToActivity(LanguageActivity::class.java, id)
            R.id.region -> goToActivity(ChooseCityActivity::class.java, id)
            R.id.navItemFeedback -> goToActivity(FeedbackActivity::class.java, id)
            R.id.navItemAppInfo -> goToActivity(AppInfoActivity::class.java, id)
//            R.id.navItemLanguage -> TODO here we do lacalization. change ic_language from russian to kyrgyz

        }
        return true

    }

    private fun goToActivity(o: Class<*>, id: Int) {
        if (checkActivity(o)) {
            val intent = Intent(this, o)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()
        }
    }

    private fun checkActivity(o: Class<*>): Boolean {
        val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn = am.getRunningTasks(1)[0].topActivity
        return !cn.shortClassName.contains(o.simpleName)
    }

    open fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    open fun showProgress() {
        if (mProgressBar == null) {
            mProgressBar = ProgressDialog(this)
            mProgressBar!!.setTitle(R.string.loading)
            mProgressBar!!.show()
        }
    }

    open fun hideProgress() {
        if (mProgressBar != null && mProgressBar!!.isShowing) mProgressBar!!.dismiss()
        mProgressBar = null
    }

    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(MyContextWrapper.wrap(newBase, Settings.getLanguage(newBase)))
        } else {
            super.attachBaseContext(newBase)
        }
    }


}
