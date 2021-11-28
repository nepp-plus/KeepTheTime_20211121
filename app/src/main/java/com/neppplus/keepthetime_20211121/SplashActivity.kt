package com.neppplus.keepthetime_20211121

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.neppplus.keepthetime_20211121.utils.ContextUtil

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

//           자동로그인 할 상황인지 검사.
           if (ContextUtil.getToken(mContext) == "") {

//               토큰 없는 상황 : 로그인 필요
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

    }
}