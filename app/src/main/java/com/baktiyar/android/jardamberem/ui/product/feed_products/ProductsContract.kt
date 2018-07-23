package com.baktiyar.android.jardamberem.ui.product.feed_products

import com.baktiyar.android.jardamberem.model.Product
import com.baktiyar.android.jardamberem.utils.IProgressBar

/**
 * Created by admin on 06.03.2018.
 */
interface ProductsContract {

    interface View : IProgressBar {
        fun onSuccessLoadProducts(list: List<Product>?)
        fun onFail(message: String)
    }

    interface Presenter {
        fun loadProducts(id: Int)
    }
}