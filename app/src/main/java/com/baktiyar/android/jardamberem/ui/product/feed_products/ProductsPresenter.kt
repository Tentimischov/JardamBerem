package com.baktiyar.android.jardamberem.ui.product.feed_products

import android.content.Context
import android.util.Log
import com.baktiyar.android.jardamberem.model.Product
import com.baktiyar.android.jardamberem.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by admin on 06.03.2018.
 */
class ProductsPresenter(val mService: ApiService, val context: Context, val mView: ProductsContract.View) : ProductsContract.Presenter {
    val TAG = "ProductsPresenter"

    override fun loadProducts(id: Int) {
        mView.showProgress()
        mService.getProductByCategory(id).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>?, response: Response<List<Product>>?) {
                if (response!!.isSuccessful && response.body() != null) {
                    mView.onSuccessLoadProducts(response.body()!!)
                } else
                    mView.onFail(response.message())
                    Log.d(TAG, response.message())
                mView.hideProgress()
            }

            override fun onFailure(call: Call<List<Product>>?, t: Throwable?) {
                mView.hideProgress()
                mView.onFail(t!!.message!!)
                Log.d(TAG, t.message!!)
                t.printStackTrace()
            }
        })
    }

}