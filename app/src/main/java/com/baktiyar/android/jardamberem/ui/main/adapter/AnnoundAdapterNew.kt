package com.baktiyar.android.jardamberem.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_vertical_main.view.*

class AnnoundAdapterNew(var data: MutableList<Announcements>, var listener: MListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_vertical_main, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        holder.itemView.title.text = model.title
        Picasso.get().load(model.imgPath).into(holder.itemView.im)
    }

    fun addData(data: MutableList<Announcements>) {
        data.addAll(data)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    interface MListener {
        fun onAnClick() {}
    }
}