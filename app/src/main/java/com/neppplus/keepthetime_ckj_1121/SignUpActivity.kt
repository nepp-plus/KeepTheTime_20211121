package com.neppplus.keepthetime_ckj_1121

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_ckj_1121.databinding.ActivitySignUpBinding
import com.neppplus.keepthetime_ckj_1121.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnEmailCheck.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()

            apiService.getRequestDuplCheck("EMAIL", inputEmail).enqueue( object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

//                        code : 200인가?

                        Log.d("회원가입", "사용해도 좋습니다.")
                    }
                    else {
                        Log.d("회원가입", "사용하면 안됩니다.")

//                        최종 결과 코드가, 200이 아닌 상황. -> (에러) 응답 String -> JSONObject변환 사용.

                        val errorBody = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBody)

                        Log.d("중복확인실패", jsonObj.toString())

                        val message = jsonObj.getString("message")

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

//                    물리적 연결 실패

                }


            } )

        }

        binding.btnOk.setOnClickListener {

//            입력값 추출

            val inputEmail =  binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

//            회원가입 기능 API 호출 -> 응답 메세지 확인 (성공시)

            apiService.putRequestSignUp(inputEmail, inputPw, inputNickname).enqueue( object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

                        val basicResponse =  response.body()!!

                        Toast.makeText(mContext, basicResponse.message, Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

    override fun setValues() {

    }
}