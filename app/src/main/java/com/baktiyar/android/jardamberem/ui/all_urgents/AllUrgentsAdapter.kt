package com.baktiyar.android.jardamberem.ui.all_urgents

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Urgent
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_all_urgent.view.*


class AllUrgentsAdapter(var data: ArrayList<Urgent>, private val mListener: OnItemClickListener) : RecyclerView.Adapter<AllUrgentsAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindV(data[position], mListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cell_all_urgent, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindV(main: Urgent, mListener: OnItemClickListener) {


            itemView.title.text = main.title
            Glide.with(itemView.context).load(main.imgPath).into(itemView.im)
            itemView.setOnClickListener {
                mListener.onUrgentDetClick(main, adapterPosition)
            }

        }


    }

    interface OnItemClickListener {
        fun onUrgentDetClick(main: Urgent, position: Int)
    }
    fun setAnnounData(data: ArrayList<Urgent>) {
        this.data = data
        notifyDataSetChanged()
    }
}