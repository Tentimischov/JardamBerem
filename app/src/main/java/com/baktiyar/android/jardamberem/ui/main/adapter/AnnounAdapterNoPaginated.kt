package com.baktiyar.android.jardamberem.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_vertical_main.view.*

class AnnounAdapterNoPaginated(var data: ArrayList<Announcements>, var mListener: OnAnnounClickNoPage) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_vertical_main, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.title.text = data[position].title
       // Glide.with(holder.itemView.context).load(data[position].imgPath).into(holder.itemView.im)
        Picasso.get().load(data[position].imgPath).into(holder.itemView.im)
        holder.itemView.setOnClickListener {
            mListener.onAnClick(data[position])
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    interface OnAnnounClickNoPage {
        fun onAnClick(data: Announcements) {}
    }

    fun setAnData(data: ArrayList<Announcements>) {
        this.data = data
        notifyDataSetChanged()
    }
}