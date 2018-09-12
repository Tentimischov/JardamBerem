package com.baktiyar.android.jardamberem.ui.forum

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Forum
import com.baktiyar.android.jardamberem.utils.RoundedImageView
import kotlinx.android.synthetic.main.cell_forum.view.*
import android.widget.LinearLayout
import android.widget.TextView



class ForumAdapter(var data: ArrayList<Forum>, var listener: MClickListener) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val LOADING = 1
    private var isLoadingAdded = false


    override fun getItemCount(): Int {
        return if (data.size == null) 0 else data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM -> viewHolder = getViewHolder(parent, inflater)
            LOADING -> {
                val v2 = inflater.inflate(R.layout.progress_bar, parent, false)
                viewHolder = LoadingVH(v2)
            }
        }

        return viewHolder!!
    }

    protected inner class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView)


    class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private fun getViewHolder(parent: ViewGroup, inflater: LayoutInflater): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val v1 = inflater.inflate(R.layout.cell_forum, parent, false)
        viewHolder = MViewHolder(v1)
        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        when (getItemViewType(position)) {
            ITEM -> {
                holder.itemView.forum_user.text = model.nickName
                holder.itemView.forum_body.text = model.comment
                if (getAndroidId(holder.itemView.context) == model.userImeiCode) {
                    holder.itemView.delete.visibility = View.VISIBLE
                }

                holder.itemView.delete.setOnClickListener {
                    listener.onForumDelete(model.id!!, holder.adapterPosition)
                }
            }
            LOADING -> {
                return
            }
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

    interface MClickListener {
        fun onForumDelete(id: Int, position: Int)
    }



    override fun getItemViewType(position: Int): Int {
        return if (position == data.size - 1 && isLoadingAdded) LOADING else ITEM;
    }

    fun add(mc: Forum) {
        data.add(mc)
        notifyItemInserted(data.size - 1)
    }

    fun addAll(mcList: List<Forum>) {
        for (mc in mcList) {
            add(mc)
        }
    }

    fun remove(city: Forum) {
        val position = data.indexOf(city)
        if (position > -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Forum(null, null, null, null, null, null))
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = data.size - 1
        val item = getItem(position)

        if (item != null) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): Forum? {
        return data[position]
    }

    fun clearList() {
        if (data.isNotEmpty()) {
            data.clear()
            notifyDataSetChanged()
        }
    }


}