package com.neppplus.keepthetime_ckj_1121.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.neppplus.keepthetime_ckj_1121.utils.ContextUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ServerAPI {

//    통신을 담당하는 객체 - 레트로핏 객체 리턴 함수.

    companion object {

//        서버 주소 => https 적용 주소 + 도메인
        private val BASE_URL = "https://keepthetime.xyz"

//        Retrofit 형태의 변수 -> OkHttpClient 처럼 실제 호출 담당.
//        레트로핏 객체는 -> 프로젝트를 통틀어 하나의 객체만 만들고 -> 여러 화면에서 공유하자.
//        객체를 하나로만 유지하자 -> 싱글턴 패턴 사용. (코드 디자인 패턴)

        private var retrofit: Retrofit? = null  // 처음에는 없다. -> 하나만 만들고 이 변수를 공유.

        fun getRetrofit(context: Context) : Retrofit {

            if (retrofit == null) {

//                통신 담당객체 아직 없으면 -> 그때만 새로 만들자.

//                API 요청을 만들면 -> 가로채서, 헤더를 추가해주자. -> 추가하고 나서 나머지 API 요청 실행.
//                  자동으로 헤더 달아두는 효과 발생.

                val interceptor = Interceptor {
                    with(it) {

                        val newRequest = request().newBuilder()
                            .addHeader("X-Http-Token", ContextUtil.getToken(context))
                            .build()

                        proceed(newRequest)

                    }
                }


//                통신 클라이언트 필요.
                val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()  // 요청을 전부 가로채서 -> 헤더를 항상 첨부하게.


//                우리가 지정한 양식의 String이 온다면 => Date형태로 변환해주는 세팅 추가.
//                파싱에 대한 세팅 추가 => 파싱 : gson이 담당. => gson 세팅.

                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter( Date::class.java, DateDeserializer() )  // Date형태로 실제 파싱 진행 클래스 추가
                    .create()


//                비어있는 retrofit 객체 생성

                retrofit =  Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(myClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Date를 자동으로 파싱하는 도구를 등록.
                    .build()

            }


//            기존에 만든게 있다면, 그냥 그대로 리턴.
            return retrofit!!

        }


    }


}