package com.neppplus.keepthetime_ckj_1121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.keepthetime_ckj_1121.R
import com.neppplus.keepthetime_ckj_1121.datas.PlaceData

class PlaceSelectRecyclerAdapter(val mContext:Context, val mList: List<PlaceData>) : RecyclerView.Adapter<PlaceSelectRecyclerAdapter.PlaceViewHolder>() {

    inner class  PlaceViewHolder(row: View) : RecyclerView.ViewHolder(row) {

//        멤버변수 - list_item 내부의 UI
        val txtPlaceName = row.findViewById<TextView>(R.id.txtPlaceName)

//        함수 - 실제 데이터 연동 bind
        fun bind( data: PlaceData ) {

            txtPlaceName.text =  data.placeName

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.place_list_item, parent, false)
        return  PlaceViewHolder(row)

    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {

        holder.bind( mList[position] )

    }

    override fun getItemCount() = mList.size

}