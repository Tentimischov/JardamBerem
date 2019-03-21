package com.baktiyar.android.jardamberem.ui.forum

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.StartApplication
import com.baktiyar.android.jardamberem.model.Forum
import kotlinx.android.synthetic.main.cell_forum.view.*


class ForumAdapter(var data: ArrayList<Forum>, var listener: OnForumClickListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val USER_IMEI_CODE: String = getAndroidId(StartApplication.INSTANCE.applicationContext)
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cell_forum, parent, false))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        holder.itemView.forum_user.text = model.nickName
        holder.itemView.forum_body.text = model.comment
        holder.itemView.date.text = model.date?.substring(0, 10)

        if (USER_IMEI_CODE == model.userImeiCode) {
            holder.itemView.delete.visibility = View.VISIBLE
        } else {
            holder.itemView.delete.visibility = View.GONE
        }

        holder.itemView.delete.setOnClickListener {
            listener.onForumDelete(model.id!!, holder.adapterPosition)
        }

    }

    fun deleteForum(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("HardwareIds")
    private fun getAndroidId(content: Context): String {
        return Settings.Secure.getString(content.contentResolver, Settings.Secure.ANDROID_ID)
    }

    interface OnForumClickListener {
        fun onForumDelete(id: Int, position: Int)
    }

    fun addForumData(data: ArrayList<Forum>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }


}