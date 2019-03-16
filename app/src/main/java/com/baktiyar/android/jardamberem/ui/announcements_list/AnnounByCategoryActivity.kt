package com.baktiyar.android.jardamberem.ui.announcements_list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.MenuItem
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.utils.Const
import com.baktiyar.android.jardamberem.utils.Const.Companion.CATEGORY_NAME
import com.baktiyar.android.jardamberem.utils.Settings
import com.baktiyar.android.jardamberem.utils.e
import kotlinx.android.synthetic.main.activity_announ_list.*
import kotlinx.android.synthetic.main.toolbar.*

class AnnounByCategoryActivity : AppCompatActivity(), AnnounCategoryContract.View, ByCategoryAdapter.OnItemClickListener {

    private var presenter: AnnounCategoryPresenter? = null
    private var adapter: ByCategoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announ_list)

        init()
        if (Settings.getSpinnerItemPosition() == 1)
            two.isChecked = true

        one.setOnClickListener {
            Settings.setSpinnerItemPosition(0)
            var id = Settings.getSpinnerItemPosition()
            e(id)
            recreateScreen()
        }
        two.setOnClickListener {
            Settings.setSpinnerItemPosition(1)
            var id = Settings.getSpinnerItemPosition()
            e(id)
            recreateScreen()
        }

    }

    private fun recreateScreen() {
        finish()
        overridePendingTransition( 0, 0)
        startActivity(intent)
        overridePendingTransition( 0, 0)
    }


    fun init() {
        title = intent.getStringExtra(CATEGORY_NAME)


        presenter = AnnounCategoryPresenter(this, applicationContext)
        adapter = ByCategoryAdapter(ArrayList(), this)
        recview.layoutManager = LinearLayoutManager(this)
        recview.isNestedScrollingEnabled = false
        recview.itemAnimator = DefaultItemAnimator()
        recview.adapter = adapter

        presenter?.getDataFirst(100, 0)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(data: Announcements) {
        val intent = Intent(this, DetailedProductActivity::class.java)
        intent.putExtra(Const.GOODS, data)
        startActivity(intent)
    }

    override fun onSuccessFirst(data: ArrayList<Announcements>) {
        adapter?.setDataAll(data)
        pro_bar.visibility = View.GONE
    }

    override fun onError(message: String) {

    }

    override fun onCallClick(number: String) {
        phoneIntent(number)
    }

    private fun phoneIntent(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
        startActivity(intent)
    }


}
