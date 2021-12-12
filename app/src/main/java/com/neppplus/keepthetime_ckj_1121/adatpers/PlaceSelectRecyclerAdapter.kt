package com.neppplus.keepthetime_ckj_1121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.keepthetime_ckj_1121.R
import com.neppplus.keepthetime_ckj_1121.datas.PlaceData

class PlaceSelectRecyclerAdapter(val mContext:Context, val mList: List<PlaceData>) : RecyclerView.Adapter<PlaceSelectRecyclerAdapter.PlaceViewHolder>() {

    inner class  PlaceViewHolder(row: View) : RecyclerView.ViewHolder(row) {



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.place_list_item, parent, false)
        return  PlaceViewHolder(row)

    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size

}