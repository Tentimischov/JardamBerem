package com.baktiyar.android.jardamberem.ui.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baktiyar.android.jardamberem.R
import com.baktiyar.android.jardamberem.model.Info
import com.baktiyar.android.jardamberem.utils.Settings
import kotlinx.android.synthetic.main.cell_info.view.*
import java.text.SimpleDateFormat
import java.util.*




class InfoAdapter(var data: ArrayList<Info>, var mListener: OnItemClickListener?) :
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
        val v1 = inflater.inflate(R.layout.cell_info, parent, false)
        viewHolder = MViewHolder(v1)
        return viewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        when (getItemViewType(position)) {
            ITEM -> {

                val  mimeType: String = "text/html"
                val  encoding: String = "UTF-8"
                val stringDate =  model.date?.substring(0, 10)

                val inputFormatter1 = SimpleDateFormat("yyyy-MM-dd", Locale(Settings.getLanguage(holder.itemView.context)))
                val date1 = inputFormatter1.parse(stringDate)

                val outputFormatter1 = SimpleDateFormat("d MMMM, yyyy",  Locale(Settings.getLanguage(holder.itemView.context)))
                val output1 = outputFormatter1.format(date1)
/*

                val dateFormat = SimpleDateFormat("yyyy/MM/dd")
                val date =  dateFormat.format(stringDate)
                val outputDate = SimpleDateFormat("dd/MMM/yyyy")
*/


                holder.itemView.info_title.text = model.title
                holder.itemView.info_date.text = output1
               // holder.itemView.info_des.loadDataWithBaseURL("", model.description, mimeType, encoding, "")
                holder.itemView.setOnClickListener {
                    mListener?.onInfoClick(data[position])
                }
            }
            LOADING -> {
                return
            }
        }


    }


    interface OnItemClickListener {
        fun onInfoClick(main: Info)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == data.size - 1 && isLoadingAdded) LOADING else ITEM;
    }

    fun add(mc: Info) {
        data.add(mc)
        notifyItemInserted(data.size - 1)
    }

    fun addAll(mcList: List<Info>) {
        for (mc in mcList) {
            add(mc)
        }
    }

    fun remove(city: Info) {
        val position = data.indexOf(city)
        if (position > -1) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Info(null, null, null, null))
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

    fun getItem(position: Int): Info? {
        return data[position]
    }


}
