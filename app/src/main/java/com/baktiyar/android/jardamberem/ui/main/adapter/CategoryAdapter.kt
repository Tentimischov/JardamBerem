package com.baktiyar.android.jardamberem.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.AllCategory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_category_main.view.*

class CategoryAdapter(var data: ArrayList<AllCategory>, private val mListener: OnItemClickListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindV(data[position], mListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_category_main, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindV(main: AllCategory, mListener: OnItemClickListener) {
            itemView.ca_title.text = main.category_name
            Picasso.get().load(main.category_imgPath).into(itemView.ca_icon)
            itemView.setOnClickListener {
                mListener.onCategoryItemClick(main, adapterPosition)
            }
        }

    }

    interface OnItemClickListener {
        fun onCategoryItemClick(main: AllCategory, position: Int)

    }

    fun setCategoryData(data: ArrayList<AllCategory>) {
        this.data = data
        notifyDataSetChanged()
    }
}