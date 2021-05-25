package com.hovanngan.mock1.callhistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hovanngan.mock1.R
import com.hovanngan.mock1.databinding.HistoryItemBinding
import com.hovanngan.mock1.model.History
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CallHistoryAdapter : RecyclerView.Adapter<CallHistoryAdapter.ViewHolder>() {
    private val data = ArrayList<History>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mBinding: HistoryItemBinding? = null

        init {
            mBinding = DataBindingUtil.bind(itemView)
        }
    }

    fun setData(data: ArrayList<History>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.mBinding!!.apply {
            tvPhoneNumber.text = item.phoneNumber
            tvTotalTime.text = getMinuteFromDuration(item.totalTime.toInt())
            tvDateCreated.text = getTimeFromTimestamp(item.dateCreated.toLong())
        }
    }

    override fun getItemCount(): Int = data.size

    private fun getTimeFromTimestamp(time: Long): String {
        return SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault()).format(time)
    }

    private fun getMinuteFromDuration(duration: Int): String {
        val sec: Int = duration % 60
        val min: Int = duration / 60 % 60
        val hours: Int = duration / 60 / 60
        return if (hours == 0) {
            "[$min:$sec]"
        } else {
            "[$hours:$min:$sec]"
        }
    }

}

