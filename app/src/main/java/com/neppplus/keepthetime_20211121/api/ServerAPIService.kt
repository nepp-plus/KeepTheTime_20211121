package com.neppplus.keepthetime_20211121.api

import com.neppplus.keepthetime_20211121.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface ServerAPIService {

//    기능별 주소 (endpoint) / 메쏘드 (POST) / 파라미터 명시.

//    POST / PUT / PATCH - FormData (제 서버 : FormUrlEncoded 처리 필요)

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email")  email: String,
        @Field("password")  pw: String
    ) : Call<BasicResponse>


//    회원가입 담당 함수 (기능) 추가

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email")  email: String,
        @Field("password")  pw: String,
        @Field("nick_name")  nickname: String
    ) : Call<BasicResponse>

    @GET("/user/check")
    fun getRequestDuplCheck (

        @Query("type") type: String,
        @Query("value") value: String

    ) : Call<BasicResponse>


//    연습 - 내 정보 가져오기 API

    @GET("/user")
    fun getRequestMyInfo(
        @Header("X-Http-Token") token: String
    ) : Call<BasicResponse>


}