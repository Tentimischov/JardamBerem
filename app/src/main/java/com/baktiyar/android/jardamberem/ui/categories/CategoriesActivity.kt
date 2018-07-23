package com.baktiyar.android.jardamberem.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.ui.BaseActivity
import com.baktiyar.android.jardamberem.ui.product.feed_products.ProductsActivity
import kotlinx.android.synthetic.main.activity_categories.*


class CategoriesActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val CATEGORY_ID: String="category_id"
        const val CATEGORY_NAME: String="category_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        init()


    }

    private fun init(){
        foodCategoryView.setOnClickListener (this)
        closeCategoryView.setOnClickListener (this)
        jobCategoryView.setOnClickListener (this)
        othersCategoryView.setOnClickListener (this)
    }

    private fun goToActivity(intent: Intent, categoryId: Int, categoryName:String){
        intent.putExtra(CATEGORY_ID, categoryId)
        intent.putExtra(CATEGORY_NAME, categoryName)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, ProductsActivity::class.java)
        when(v){
            foodCategoryView -> goToActivity(intent, 1, "Еда")
            closeCategoryView -> goToActivity(intent, 2, "Одежда")
            jobCategoryView -> goToActivity(intent, 3, "Работа")
            othersCategoryView -> goToActivity(intent, 4, "Другое")
        }
    }

}
