package com.neppplus.keepthetime_20211121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20211121.api.ServerAPI
import com.neppplus.keepthetime_20211121.api.ServerAPIService
import com.neppplus.keepthetime_20211121.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnLogin.setOnClickListener {

//            1. 입력 email / pw 변수 담자
            val inputEmail =  binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()


//            2. 서버에 로그인 API 호출 -> Retrofit



        }

    }

    override fun setValues() {

    }
}