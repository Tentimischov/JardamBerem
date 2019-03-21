package com.baktiyar.android.jardamberem.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Urgent
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_horizontal_main.view.*

class UrgentAdapter(var data: ArrayList<Urgent>,
                    var mClickListener: OnUrgClickListener
) : RecyclerView.Adapter<UrgentAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindV(data[position], mClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_horizontal_main, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setUrgData(data: ArrayList<Urgent>) {
        this.data = data
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindV(main: Urgent, mClickListener: OnUrgClickListener) {
            itemView.cell_hor.preventCornerOverlap = false
            itemView.title.text = main.title
            Glide.with(itemView.context).load(main.imgPath).into(itemView.icon)
            itemView.setOnClickListener {
                mClickListener.onUrgentItemClick(main)
            }
        }


    }

    interface OnUrgClickListener {
        fun onUrgentItemClick(main: Urgent)
    }
}