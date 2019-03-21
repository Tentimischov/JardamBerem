package com.baktiyar.android.jardamberem.ui.announcements_list

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_vertical_main.view.*

class ByCategoryAdapter(var data: ArrayList<Announcements>, var mListener: OnItemClickListener) : RecyclerView.Adapter<ByCategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_vertical_main, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]

        holder.itemView.title.text = model.title

        holder.itemView.date.text = model.date?.substring(0, 10)
        holder.itemView.description.text = model.description
        if (model.imgPath != null) {
            Glide.with(holder.itemView.context)
                    .load(model.imgPath)
                    .into(holder.itemView.im)
        } else {
            holder.itemView.im.layoutParams.height = (holder.itemView.context.resources.displayMetrics.widthPixels / 3)
        }

        holder.itemView.call.setOnClickListener {
            mListener.onCallClick(model.number!!)
        }


       // holder.itemView.im.width = data[position].imgPath_width?.toInt()!!
        holder.itemView.setOnClickListener{
            mListener.onItemClick(data[position])
        }

    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    }

    interface OnItemClickListener {
        fun onItemClick(data: Announcements)
        fun onCallClick(number: String)
    }

    fun setDataAll(data: ArrayList<Announcements>) {
        this.data = data
        notifyDataSetChanged()
    }

}