package com.baktiyar.android.jardamberem.ui.announcements_list

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
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
        holder.itemView.title.text = data[position].title
        val wid = holder.itemView.context.resources.displayMetrics.widthPixels / 2
        if (data[position].imgPath != null) {
            Picasso.get().load(data[position].imgPath).into(holder.itemView.im)
            if (data[position].imgPath_height?.toInt()!! > data[position].imgPath_width?.toInt()!!) {
                holder.itemView.im.layoutParams.height = (wid + Math.abs(data[position].imgPath_height?.toInt()!! - data[position].imgPath_width?.toInt()!!)) / 2
            } else {
                holder.itemView.im.layoutParams.height = 200 * (data[position].imgPath_width?.toInt()!! / data[position].imgPath_height?.toInt()!!)
            }
        } else {
            holder.itemView.im.layoutParams.height = (holder.itemView.context.resources.displayMetrics.widthPixels / 3)
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
    }

    fun setDataAll(data: ArrayList<Announcements>) {
        this.data = data
        notifyDataSetChanged()
    }

}