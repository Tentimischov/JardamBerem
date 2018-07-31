package com.baktiyar.android.jardamberem.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Announcements
import com.baktiyar.android.jardamberem.utils.RoundedImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_vertical_main.view.*

class AnnounAdapter(var data: ArrayList<Announcements>, var mListener: OnItemClickListener?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val LOADING = 1
    private var isLoadingAdded = false


    override fun getItemCount(): Int {
        return data.size
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
        val v1 = inflater.inflate(R.layout.cell_vertical_main, parent, false)
        viewHolder = MViewHolder(v1)
        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        when (getItemViewType(position)) {
            ITEM -> {
                holder.itemView.title.text = model.title
                Picasso.get().load(model.imgPath).into(holder.itemView.im)

                holder.itemView.setOnClickListener {
                    mListener?.onAnnounClick(data[position])
                }
            }
            LOADING -> {
                return
            }
        }


    }


    interface OnItemClickListener {
        fun onAnnounClick(main: Announcements)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == data.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun add(mc: Announcements) {
        data.add(mc)
        notifyItemInserted(data.size - 1)
    }

    fun addAll(mcList: List<Announcements>) {
        for (mc in mcList) {
            add(mc)
        }
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Announcements(null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null))
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

    fun getItem(position: Int): Announcements? {
        return data[position]
    }

    fun clearList() {
        if (data.isNotEmpty()) {
            data.clear()
            notifyDataSetChanged()
        }
    }


}