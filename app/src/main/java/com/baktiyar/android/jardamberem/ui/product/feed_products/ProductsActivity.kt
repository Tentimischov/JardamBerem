package com.baktiyar.android.jardamberem.ui.product.feed_products


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.product.feed_products.adapter.ViewPagerAdapter
import com.baktiyar.android.jardamberem.utils.HelpStatus
import kotlinx.android.synthetic.main.activity_tabs.*

class ProductsActivity : AppCompatActivity() {
    lateinit var app: StartApplication
    lateinit var adapter: ViewPagerAdapter
    lateinit var toolbar: Toolbar
    companion object {
        const val PRODUCT_INTENT: String = "parced_product"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        app = application as StartApplication
        initToolbar()

        initViewPager()
    }

    fun initViewPager() {
        adapter = ViewPagerAdapter(supportFragmentManager)

        val bundle: Bundle = intent.extras

        adapter.addFragment(ProductsFragment.getInstance(HelpStatus.GIVE, bundle), getString(R.string.give_help))
        adapter.addFragment(ProductsFragment.getInstance(HelpStatus.TAKE, bundle), getString(R.string.need_help))

        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(R.color.colorPrimary))
      //  toolbar.toolbar_title.text = intent.getStringExtra(CATEGORY_NAME)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}
