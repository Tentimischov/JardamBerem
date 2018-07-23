package com.baktiyar.android.jardamberem.ui.product.feed_products.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Product
import com.baktiyar.android.jardamberem.utils.RoundedImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*


/**
 * Created by admin on 07.03.2018.
 */
class ProductsAdapter(var mProductList: List<Product>,
                      val mListener: OnItemClickListener) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>(){




    interface OnItemClickListener {
        fun onItemClick(product: Product, v: View)/*
        fun onLongItemClick(product: Product)*/
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindGood(mProductList[position], mListener)
    }

    fun setList(list: List<Product>) {
        mProductList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindGood(product: Product, listener: OnItemClickListener) {
            itemView.tvGoodName.text = product.title
            itemView.tvGoodName.setRawInputType(10)
            if (product.firstImage!=null) {
                Picasso.get().load(product.firstImage).into(itemView.ivGoodImage)
                /*Glide.with(itemView.context)
                        .load(product.firstImage)
                        .apply(RequestOptions().centerCrop().placeholder(R.drawable.img_no_photo))
                        .into(itemView.ivGoodImage)*/
            }
            itemView.setOnClickListener {
                listener.onItemClick(product, itemView.ivGoodImage)
            }
        }
    }
}