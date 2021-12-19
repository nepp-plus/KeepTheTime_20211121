package com.neppplus.keepthetime_ckj_1121.api

import com.neppplus.keepthetime_ckj_1121.datas.BasicResponse
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


    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider: String,
        @Field("uid") uid: String,
        @Field("nick_name") nickname: String,
    ) : Call<BasicResponse>


    @GET("/user/friend")
    fun getRequestMyFriends(
        @Query("type") type: String,
    ) : Call<BasicResponse>


    @GET("/search/user")
    fun getRequestSearchFriend(
        @Query("nickname") nickname: String,
    ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/friend")
    fun postRequestAddFriend(
        @Field("user_id") id: Int,
    ) : Call<BasicResponse>

    @FormUrlEncoded
    @PUT("/user/friend")
    fun putRequestAcceptOrDenyFriendRequest(
        @Field("user_id") id: Int,
        @Field("type") type: String,
    ) : Call<BasicResponse>


    @FormUrlEncoded
    @POST("/appointment")
    fun postRequestAppointment(
        @Field("title") title: String,
        @Field("datetime") datetime: String,
        @Field("place") place: String,
        @Field("latitude") lat: Double,
        @Field("longitude") lng: Double,
    ) : Call<BasicResponse>

    @GET("/appointment")
    fun getRequestAppointment() : Call<BasicResponse>


//    연습 - 내 정보 가져오기 API

    @GET("/user")
    fun getRequestMyInfo() : Call<BasicResponse>


//    내 출발 장소 등록하기

    @FormUrlEncoded
    @POST("/user/place")
    fun postRequestAddStartingPoint(
        @Field("name") name: String,
        @Field("latitude") lat: Double,
        @Field("longitude") lng: Double,
        @Field("is_primary") isPrimary: Boolean,
    ) : Call<BasicResponse>



}