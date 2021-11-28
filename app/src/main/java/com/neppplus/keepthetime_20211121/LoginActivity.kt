package com.neppplus.keepthetime_20211121

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.neppplus.keepthetime_20211121.databinding.ActivityLoginBinding
import com.neppplus.keepthetime_20211121.datas.BasicResponse
import com.neppplus.keepthetime_20211121.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.util.*

class LoginActivity : BaseActivity() {

    lateinit var binding : ActivityLoginBinding

//    페북 로그인에 다녀오면 할일을 관리해주는 변수.
    lateinit var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnKakaoLogin.setOnClickListener {


        }

        binding.btnFacebookLogin.setOnClickListener {

//            소셜 로그인 로직 체험

//            페북로그인에 성공하게 되면 할일도 미리 설정.

            LoginManager.getInstance().registerCallback( callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {

//                    페북 로그인 성공 -> 그 결과로 토큰 받아오기 성공.

                    Log.d("로그인성공", AccessToken.getCurrentAccessToken().toString())

//                    이 토큰을 이용해서 -> 페북에서 사용자 정보도 받아오자. => 페북 클래스 : GraphRequest

                    GraphRequest.newMeRequest(result?.accessToken, object : GraphRequest.GraphJSONObjectCallback {

                        override fun onCompleted(jsonObj: JSONObject?, response: GraphResponse?) {

                            Log.d("내정보",  jsonObj.toString())
                            Log.d("내정보응답",  response.toString())
                        }

                    })

                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {

                }

            } )

            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList( "public_profile", "email" ))

        }

       binding.btnSignUp.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        binding.btnLogin.setOnClickListener {

//            1. 입력 email / pw 변수 담자
            val inputEmail =  binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()


//            2. 서버에 로그인 API 호출 -> Retrofit

            apiService.postRequestLogin(inputEmail, inputPassword).enqueue( object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

//                    최종 성공 / 실패 여부에 따라 별도 코딩
                    if (response.isSuccessful) {
                        val basicResponse =  response.body()!!

                        ContextUtil.setToken(mContext, basicResponse.data.token)

                        Toast.makeText(
                            mContext,
                            "${basicResponse.data.user.nickname}님 환영합니다!",
                            Toast.LENGTH_SHORT
                        ).show()

//                        메인으로 이동
                        val myIntent = Intent(mContext,  MainActivity::class.java)
                        startActivity(myIntent)
                        
                        finish() // 로그인 화면 종료

                    }
                    else {

//                        로그인 결과 실패. => 실패 사유 (message) 토스트
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)

                        val message = jsonObj.getString("message")

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    Toast.makeText(mContext, "서버 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()

                }

            } )


        }

    }

    override fun setValues() {
        getKeyHash()

//        페북로그인 - 콜백 관리 기능 생성
        callbackManager = CallbackManager.Factory.create()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getKeyHash() {
        val info = packageManager.getPackageInfo(
            "com.neppplus.keepthetime_20211121",
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }
    }

}