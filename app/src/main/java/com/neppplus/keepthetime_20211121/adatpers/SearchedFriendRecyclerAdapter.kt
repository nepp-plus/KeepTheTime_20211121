package com.neppplus.keepthetime_20211121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20211121.R
import com.neppplus.keepthetime_20211121.datas.UserData

class SearchedFriendRecyclerAdapter(val mContext: Context, val mList: List<UserData>) : RecyclerView.Adapter<SearchedFriendRecyclerAdapter.SearchedUserViewHolder>() {

    inner class SearchedUserViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)
        val btnAddFriend = row.findViewById<Button>(R.id.btnAddFriend)

        fun bind( data: UserData ) {

            txtNickname.text = data.nickname
            Glide.with(mContext).load(data.profileImageURL).into(imgProfile)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedUserViewHolder {
        val row =  LayoutInflater.from(mContext).inflate(R.layout.searched_friend_list_item, parent, false)
        return SearchedUserViewHolder(row)
    }

    override fun onBindViewHolder(holder: SearchedUserViewHolder, position: Int) {

        holder.bind( mList[position] )

    }

    override fun getItemCount() = mList.size

}