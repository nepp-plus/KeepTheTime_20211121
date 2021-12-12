package com.neppplus.keepthetime_ckj_1121.adatpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_ckj_1121.AddFriendActivity
import com.neppplus.keepthetime_ckj_1121.R
import com.neppplus.keepthetime_ckj_1121.datas.BasicResponse
import com.neppplus.keepthetime_ckj_1121.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchedFriendRecyclerAdapter(val mContext: Context, val mList: List<UserData>) : RecyclerView.Adapter<SearchedFriendRecyclerAdapter.SearchedUserViewHolder>() {

    inner class SearchedUserViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickname = row.findViewById<TextView>(R.id.txtNickname)
        val btnAddFriend = row.findViewById<Button>(R.id.btnAddFriend)

        fun bind( data: UserData ) {

            txtNickname.text = data.nickname
            Glide.with(mContext).load(data.profileImageURL).into(imgProfile)

//            이벤트 처리

            btnAddFriend.setOnClickListener {

//                친구 추가 API 호출 -> 어댑터 내부에서 호출

//                화면 : mContext (Context)  => 실제 담긴것 : AddFriendActivity

                (mContext as AddFriendActivity).apiService.postRequestAddFriend(data.id).enqueue( object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "친구 추가에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                } )


            }

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