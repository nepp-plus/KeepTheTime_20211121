package com.neppplus.keepthetime_20211121_ckj

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20211121_ckj.adatpers.MainViewPagerAdapter
import com.neppplus.keepthetime_20211121_ckj.databinding.ActivityMainBinding
import com.neppplus.keepthetime_20211121_ckj.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var mvpa : MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mvpa = MainViewPagerAdapter(supportFragmentManager)
        binding.mainViewPager.adapter = mvpa

        binding.mainTabLayout.setupWithViewPager(  binding.mainViewPager  )

//        getMyInfoFromServer()
    }

//    연습 -  내 정보를 서버에서 받아오기 (GET / Header 첨부)

    fun getMyInfoFromServer() {

        apiService.getRequestMyInfo().enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val basicResponse = response.body()!!

                    Log.d("응답내용", basicResponse.data.user.nickname)

//                    binding.txtNickname.text = basicResponse.data.user.nickname

//                    사용자의 프사 표시

//                    Glide.with(mContext).load(basicResponse.data.user.profileImageURL).into(binding.imgProfile)

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}