package com.baktiyar.android.jardamberem.ui.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Info
import kotlinx.android.synthetic.main.cell_info.view.*

class InfoAdapterNoPag(var data: ArrayList<Info>, var mListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_info, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.info_title.text = data[position].title
      //  holder.itemView.info_des.loadUrl(data[position].description)
        holder.itemView.info_date.text = data[position].date
    }

    fun setInfo(data: ArrayList<Info>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    interface OnItemClickListener {
        fun onInfoClick(info: Info)
    }
}