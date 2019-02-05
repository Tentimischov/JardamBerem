package com.baktiyar.android.jardamberem.ui.product.post_product


import com.baktiyar.android.jardamberem.model.AllCategory
import com.baktiyar.android.jardamberem.model.PostProduct
import com.baktiyar.android.jardamberem.model.PostUrgentProduct
import com.baktiyar.android.jardamberem.utils.IProgressBar


/**
 * Created by admin on 09.03.2018.
 */
interface NewProductContract{
    interface View: IProgressBar{
        fun onPostProductSuccess()
        fun onPostProductError(message: String)
        fun onCategorySuccess(data: ArrayList<AllCategory>)
        fun onCategoryError(message: String)
    }
    interface Presenter{
        fun sendProduct(categoryId: Int, city: Int, item: PostProduct, paths: ArrayList<String>?)
        fun sendUrgentProduct(item: PostUrgentProduct, paths: ArrayList<String>?)
        fun getCategory()
    }
}