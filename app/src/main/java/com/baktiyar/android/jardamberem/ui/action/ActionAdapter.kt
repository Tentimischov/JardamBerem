package com.baktiyar.android.jardamberem.ui.action

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.ActionData
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cell_action.view.*

class ActionAdapter(var data: ArrayList<ActionData>, var mListener: OnItemClickListener?) :
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
        val v1 = inflater.inflate(R.layout.cell_action, parent, false)
        viewHolder = MViewHolder(v1)
        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        when (getItemViewType(position)) {
            ITEM -> {
                holder.itemView.action_title.text = model.title

                var ok = true

                if (model.imgPath != null) {
                    ok = false
                    Picasso.get().load(model.imgPath).into(holder.itemView.action_image)
                    Log.e("___________", model.imgPath)
                } else if (model.imgPath2 != null && ok) {
                    ok = false
                    Picasso.get().load(model.imgPath2).into(holder.itemView.action_image)
                } else if (model.imgPath3 != null && ok) {
                    Picasso.get().load(model.imgPath3).into(holder.itemView.action_image)
                }

                holder.itemView.setOnClickListener {
                    mListener?.onAnnounClick(data[position], position)
                }
            }
            LOADING -> {
                return
            }
        }


    }


    interface OnItemClickListener {
        fun onAnnounClick(main: ActionData, position: Int)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == data.size - 1 && isLoadingAdded) LOADING else ITEM;
    }

    fun add(mc: ActionData) {
        data.add(mc)
        notifyItemInserted(data.size - 1)
    }

    fun addAll(mcList: List<ActionData>) {
        for (mc in mcList) {
            add(mc)
        }
    }

    fun remove(city: ActionData) {
        val position = data.indexOf(city)
        if (position > -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ActionData(null, null, null, null, null, null, null))
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

    fun getItem(position: Int): ActionData? {
        return data[position]
    }


}
