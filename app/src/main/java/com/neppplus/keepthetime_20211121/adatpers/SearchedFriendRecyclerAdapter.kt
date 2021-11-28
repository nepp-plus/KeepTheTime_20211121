package com.neppplus.keepthetime_20211121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.keepthetime_20211121.R
import com.neppplus.keepthetime_20211121.datas.UserData

class SearchedFriendRecyclerAdapter(val mContext: Context, val mList: List<UserData>) : RecyclerView.Adapter<SearchedFriendRecyclerAdapter.SearchedUserViewHolder>() {

    inner class SearchedUserViewHolder(row: View) : RecyclerView.ViewHolder(row) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedUserViewHolder {
        val row =  LayoutInflater.from(mContext).inflate(R.layout.searched_friend_list_item, parent, false)
        return SearchedUserViewHolder(row)
    }

    override fun onBindViewHolder(holder: SearchedUserViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size

}