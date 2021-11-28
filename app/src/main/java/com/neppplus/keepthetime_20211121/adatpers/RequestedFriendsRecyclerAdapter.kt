package com.neppplus.keepthetime_20211121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20211121.R
import com.neppplus.keepthetime_20211121.datas.UserData

class RequestedFriendsRecyclerAdapter(val mContext: Context, val mList: List<UserData>) : RecyclerView.Adapter<RequestedFriendsRecyclerAdapter.RequestedFriendViewHolder>() {

    inner class RequestedFriendViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)

        fun bind( data: UserData ) {

            txtNickname.text = data.nickname
            Glide.with(mContext).load(data.profileImageURL).into(imgProfile)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestedFriendViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.requested_friend_list_item, parent, false)
        return RequestedFriendViewHolder(row)

    }

    override fun onBindViewHolder(holder: RequestedFriendViewHolder, position: Int) {

        holder.bind( mList[position] )

    }

    override fun getItemCount() = mList.size

}