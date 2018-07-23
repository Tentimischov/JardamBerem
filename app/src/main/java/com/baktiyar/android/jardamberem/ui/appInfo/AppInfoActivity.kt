package com.baktiyar.android.jardamberem.ui.appInfo


import android.os.Bundle
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.BaseActivity

class AppInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        title = getString(R.string.about_app)
    }
}
