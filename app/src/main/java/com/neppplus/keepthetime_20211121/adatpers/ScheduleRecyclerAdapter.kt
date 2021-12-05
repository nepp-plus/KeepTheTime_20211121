package com.neppplus.keepthetime_20211121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.keepthetime_20211121.R
import com.neppplus.keepthetime_20211121.datas.ScheduleData

class ScheduleRecyclerAdapter(val mContext: Context, val mList: List<ScheduleData>) : RecyclerView.Adapter<ScheduleRecyclerAdapter.ScheduleViewHolder>() {

    inner class ScheduleViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val txtAppointmentTitle = row.findViewById<TextView>(R.id.txtAppointmentTitle)
        val txtAppointmentPlace = row.findViewById<TextView>(R.id.txtAppointmentPlace)
        val txtDateTime = row.findViewById<TextView>(R.id.txtDateTime)
        val imgMap = row.findViewById<ImageView>(R.id.imgMap)

        fun bind( data: ScheduleData ) {

            txtAppointmentTitle.text =  data.title
            txtAppointmentPlace.text = data.place

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.schedule_list_item, parent, false)
        return ScheduleViewHolder(row)

    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {

        holder.bind( mList[position] )

    }

    override fun getItemCount() = mList.size

}