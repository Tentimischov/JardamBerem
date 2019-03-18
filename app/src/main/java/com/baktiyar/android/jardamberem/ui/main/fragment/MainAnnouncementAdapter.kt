package com.baktiyar.android.jardamberem.ui.main.fragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_vertical_main.view.*

class MainAnnouncementAdapter(
        var data: ArrayList<Announcements>,
        var mListener: OnItemClickListener?) : RecyclerView.Adapter<MainAnnouncementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_vertical_main, parent, false))
    }

    override fun getItemCount(): Int {
        return if (data.isEmpty()) 0
                else data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]

        if (model.imgPath != null) {
            Picasso.get().load(model.imgPath).into(holder.itemView.im)
        }

        holder.itemView.title.text = model.title
        holder.itemView.date.text = model.date?.substring(0, 10)
        holder.itemView.description.text = model.description

        holder.itemView.call.setOnClickListener {
            mListener?.onCallClick(model.number!!)
        }
        holder.itemView.setOnClickListener {
            mListener?.onAnnouncementItemClick(data[position])
        }
    }

    fun addAnnouncementData(data: ArrayList<Announcements>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    interface OnItemClickListener {
        fun onAnnouncementItemClick(main: Announcements)
        fun onCallClick(number: String)
    }
}