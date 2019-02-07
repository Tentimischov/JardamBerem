package com.baktiyar.android.jardamberem.ui.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Info
import kotlinx.android.synthetic.main.cell_info.view.*
import java.util.*


class InfoAdapter(var data: ArrayList<Info>, var mListener: OnItemClickListener?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_info, parent,false))
    }

    protected inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        val stringDate = model.date?.substring(0, 10)

        holder.itemView.info_title.text = model.title
        holder.itemView.info_date.text = stringDate
        holder.itemView.setOnClickListener {
            mListener?.onInfoClick(data[position])
        }
    }


    interface OnItemClickListener {
        fun onInfoClick(main: Info)
    }

    fun addInfoData(data: ArrayList<Info>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }


}
