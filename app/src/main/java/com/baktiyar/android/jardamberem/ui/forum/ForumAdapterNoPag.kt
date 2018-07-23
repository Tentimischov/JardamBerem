package com.baktiyar.android.jardamberem.ui.forum

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Forum
import kotlinx.android.synthetic.main.cell_forum.view.*

class ForumAdapterNoPag(var data: ArrayList<Forum>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_forum, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.forum_user.text = data[position].nickName
        holder.itemView.forum_body.text = data[position].comment
        holder.itemView.forum_user.includeFontPadding = false
        holder.itemView.forum_body.includeFontPadding = false

       // holder.itemView.date.text = data[position].date
    }

    inner class ViewHolder(v: View): RecyclerView.ViewHolder(v)

    fun setForumData(data: ArrayList<Forum>) {
        this.data = data
        notifyDataSetChanged()
    }
}