package com.neppplus.keepthetime_20211121_ckj.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20211121_ckj.R
import com.neppplus.keepthetime_20211121_ckj.datas.UserData

class MyFriendsRecyclerAdapter(val mContext: Context, val mList: List<UserData>) : RecyclerView.Adapter<MyFriendsRecyclerAdapter.MyFriendViewHolder>() {

    inner class MyFriendViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)

        fun bind( data: UserData ) {

            txtNickname.text = data.nickname
            Glide.with(mContext).load(data.profileImageURL).into(imgProfile)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFriendViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.my_friend_list_item, parent, false)
        return MyFriendViewHolder(row)

    }

    override fun onBindViewHolder(holder: MyFriendViewHolder, position: Int) {

        holder.bind( mList[position] )

    }

    override fun getItemCount() = mList.size

}