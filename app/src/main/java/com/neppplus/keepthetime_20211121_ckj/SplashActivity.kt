package com.neppplus.keepthetime_20211121_ckj

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.neppplus.keepthetime_20211121_ckj.datas.BasicResponse
import com.neppplus.keepthetime_20211121_ckj.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        2초가 지나면 어느 화면으로 가야할지 (메인 Vs. 로그인) 결정 -> 이동

        val myHandler = Handler( Looper.getMainLooper() )  // UI 담당 쓰레드를 불러내는 역할.

        myHandler.postDelayed( {

           val myIntent : Intent

//           자동로그인 할 상황인지 검사. =>  내가 갖고 있는 토큰이, 유효한 토큰인가?
//           내 정보 API를 통해서 -> 사용자 정보가 저장되었는가?
           if ( GlobalData.loginUser == null ) {

//               토큰 없거나 or 있지만 유효하지 않으면 => 로그인
               myIntent = Intent(mContext, LoginActivity::class.java)

           }
           else {
//                자동 로그인 해도 됨.
               myIntent = Intent(mContext, MainActivity::class.java)
           }

            startActivity(myIntent)

            finish()

        }, 2000 )

    }

    override fun setValues() {
        testToken()
    }

    fun testToken() {

//        ContextUtil에 저장한 토큰이 괜찮은토큰인가? 검사 -> 내 정보 불러오기. 잘 불러오는지 확인.
        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
//                    내 정보 가져옴.
                    val br = response.body()!!

//                    GlobalData의 로그인사용자에 사용자 정보를 저장.
                    GlobalData.loginUser =  br.data.user

                }
                else {
//                    내 정보 불러오기 실패.
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}