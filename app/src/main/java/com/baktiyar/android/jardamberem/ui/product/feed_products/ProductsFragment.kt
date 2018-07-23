package com.baktiyar.android.jardamberem.ui.product.feed_products

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.*
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Product
import com.baktiyar.android.jardamberem.ui.categories.CategoriesActivity
import com.baktiyar.android.jardamberem.ui.product.detailed_product.DetailedProductActivity
import com.baktiyar.android.jardamberem.ui.product.feed_products.adapter.ProductsAdapter
import com.baktiyar.android.jardamberem.ui.product.post_product.NewProductActivity
import com.baktiyar.android.jardamberem.utils.Const.Companion.IMAGE_URL_EXTRA
import com.baktiyar.android.jardamberem.utils.Const.Companion.PROPNAME_HEIGHT
import com.baktiyar.android.jardamberem.utils.Const.Companion.PROPNAME_SCREENLOCATION_LEFT
import com.baktiyar.android.jardamberem.utils.Const.Companion.PROPNAME_SCREENLOCATION_TOP
import com.baktiyar.android.jardamberem.utils.Const.Companion.PROPNAME_WIDTH
import com.baktiyar.android.jardamberem.utils.Const.Companion.VIEW_INFO_EXTRA
import com.baktiyar.android.jardamberem.utils.HelpStatus
import kotlinx.android.synthetic.main.fragment_products.*
import org.jetbrains.anko.toast


/**
 * Created by admin on 03.05.2018.
 */

class ProductsFragment : Fragment(), ProductsContract.View, ProductsAdapter.OnItemClickListener {
    companion object {
        fun getInstance(status: HelpStatus, bundle: Bundle): ProductsFragment {
            bundle.putSerializable(HELP_STATUS, status)
            val fragment = ProductsFragment()
            fragment.arguments = bundle
            return fragment
        }

        const val HELP_STATUS: String = "help status"
    }

    var mActivity = activity as ProductsActivity?
    private var mPresenter: ProductsPresenter? = null
    private var mAdapter: ProductsAdapter? = null
    private var myIntent: Intent? = null
    private var mCategoryId: Int? = null
    private var mCategoryName: String? = null
    private var mProductsAmount: Int? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        var view = inflater?.inflate(R.layout.fragment_products, container, false)
        mActivity = activity as ProductsActivity?
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGetIntent()
        init()
    }

    fun initGetIntent() {
        var bundle = arguments
        mCategoryId = bundle!!.getInt(CategoriesActivity.CATEGORY_ID, -1)
        mCategoryName = bundle.getString(CategoriesActivity.CATEGORY_NAME)
    }

    override fun onResume() {
        super.onResume()
        updateProducts()
    }

    private fun init() {
        refreshLayoutProductsList.setOnRefreshListener {
            updateProducts()
        }
        initPresenter()
        initRecyclerView()
        initToolbar()
    }

    private fun initPresenter() {
        mPresenter = ProductsPresenter(mActivity!!.app.service!!, mActivity!!, this)
        mPresenter!!.loadProducts(mCategoryId!!)
    }

    private fun initToolbar() {
        mActivity!!.title = mCategoryName
    }

    private fun initRecyclerView() {
        mAdapter = ProductsAdapter(ArrayList(), this)
        rvCoodsList.adapter = mAdapter
        rvCoodsList.layoutManager = GridLayoutManager(mActivity, 2)
    }

    override fun onSuccessLoadProducts(list: List<Product>?) {
        if (list != null) {
            if (list.isEmpty()) {
                rvCoodsList.visibility = View.GONE
                layoutNoProducts.visibility = View.VISIBLE
            } else {
                mAdapter!!.setList(list)
                rvCoodsList.visibility = View.VISIBLE
                layoutNoProducts.visibility = View.GONE
            }
        }
    }

    override fun onFail(message: String) {
        mActivity!!.toast(R.string.wrong_response)
    }

    private fun updateProducts() {
        mPresenter!!.loadProducts(mCategoryId!!)
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.products, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.menuItemAddProduct -> {
                val i = Intent(mActivity, NewProductActivity::class.java)
                i.putExtra(CategoriesActivity.CATEGORY_ID, mCategoryId)
                startActivity(i)
            }
            android.R.id.home -> mActivity!!.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onItemClick(product: Product, v: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            /*val intent = Intent(context, DetailedProductActivity::class.java)
            intent.putExtra(IMAGE_URL_EXTRA, product.firstImage)
            intent.putExtra(VIEW_INFO_EXTRA, *//* start values *//* captureValues(v))
            startActivity(intent)
            this.mActivity!!.overridePendingTransition(0, 0)*/
            myIntent = Intent(activity, DetailedProductActivity::class.java)
            myIntent!!.putExtra(ProductsActivity.PRODUCT_INTENT, product)
            startActivity(myIntent)
        } else {
            // Swap without transition
            myIntent = Intent(activity, DetailedProductActivity::class.java)
            myIntent!!.putExtra(ProductsActivity.PRODUCT_INTENT, product)
            startActivity(myIntent)
        }

    }

    private fun captureValues(view: View): Bundle {
        val b = Bundle()
        val screenLocation = IntArray(2)
        view.getLocationOnScreen(screenLocation)
        b.putInt(PROPNAME_SCREENLOCATION_LEFT, screenLocation[0])
        b.putInt(PROPNAME_SCREENLOCATION_TOP, screenLocation[1])
        b.putInt(PROPNAME_WIDTH, view.width)
        b.putInt(PROPNAME_HEIGHT, view.height)
        return b
    }

    override fun showProgress() {
        refreshLayoutProductsList.isRefreshing = true
    }


    override fun hideProgress() {
        refreshLayoutProductsList.isRefreshing = false
    }


}
