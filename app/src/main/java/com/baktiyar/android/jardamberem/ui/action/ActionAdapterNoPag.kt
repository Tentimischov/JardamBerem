package com.baktiyar.android.jardamberem.ui.action

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.ActionData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_action.view.*

class ActionAdapterNoPag(var data: ArrayList<ActionData>, var mListener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_action, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.action_title.text = data[position].title
        when {
            data[position].imgPath != null -> Picasso.get().load(data[position].imgPath).into(holder.itemView.action_image)
            data[position].imgPath2 != null -> Picasso.get().load(data[position].imgPath2).into(holder.itemView.action_image)
            else -> Picasso.get().load(data[position].imgPath3).into(holder.itemView.action_image)
        }
        holder.itemView.setOnClickListener {
            mListener.onItemClickListener(data[position])
        }
    }

    fun setActionData(data: ArrayList<ActionData>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

    interface OnItemClickListener {
        fun onItemClickListener(actionData: ActionData)
    }
}