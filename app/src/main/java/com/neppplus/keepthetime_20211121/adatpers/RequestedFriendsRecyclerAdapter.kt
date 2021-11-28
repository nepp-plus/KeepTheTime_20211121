package com.neppplus.keepthetime_20211121.adatpers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20211121.R
import com.neppplus.keepthetime_20211121.ViewFriendListActivity
import com.neppplus.keepthetime_20211121.datas.BasicResponse
import com.neppplus.keepthetime_20211121.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestedFriendsRecyclerAdapter(val mContext: Context, val mList: List<UserData>) : RecyclerView.Adapter<RequestedFriendsRecyclerAdapter.RequestedFriendViewHolder>() {

    inner class RequestedFriendViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)
        val btnAcceptFriend = row.findViewById<Button>(R.id.btnAcceptFriend)
        val btnDenyFriend = row.findViewById<Button>(R.id.btnDenyFriend)

        fun bind( data: UserData ) {

            txtNickname.text = data.nickname
            Glide.with(mContext).load(data.profileImageURL).into(imgProfile)

            val ocl = View.OnClickListener {

                val tag = it.tag.toString()

                (mContext as ViewFriendListActivity)
                    .apiService
                    .putRequestAcceptOrDenyFriendRequest(data.id, tag)
                    .enqueue( object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {

                            Toast.makeText(mContext, "친구요청에 응답했습니다.", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                } )

            }

            btnAcceptFriend.setOnClickListener( ocl )
            btnDenyFriend.setOnClickListener( ocl )


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