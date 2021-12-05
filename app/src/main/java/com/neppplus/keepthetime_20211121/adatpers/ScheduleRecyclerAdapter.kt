package com.neppplus.keepthetime_20211121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.keepthetime_20211121.R
import com.neppplus.keepthetime_20211121.datas.ScheduleData

class ScheduleRecyclerAdapter(val mContext: Context, val mList: List<ScheduleData>) : RecyclerView.Adapter<ScheduleRecyclerAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.schedule_list_item, parent, false)
        return ScheduleViewHolder(row)

    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size

}