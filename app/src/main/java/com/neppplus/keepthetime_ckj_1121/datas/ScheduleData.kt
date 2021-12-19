package com.neppplus.keepthetime_ckj_1121.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class ScheduleData(

    var id: Int,
    var title: String,
    var datetime: Date,
    @SerializedName("start_place")
    var startPlace: String,
    @SerializedName("start_latitude")
    var startLatitude: Double,
    @SerializedName("start_longitude")
    var startLongitude: Double,
    var place: String,
    var latitude: Double,
    var longitude: Double,
    @SerializedName("created_at")
    var createdAt: Date,

) : Serializable {



    fun getFormattedDatetime() : String {

//    datetime (약속일시) : Date로 저장되어있다.
//    기능 추가 -> Date => 가공된 String으로 내보내주는 함수.
        val twoLineFormat = SimpleDateFormat( "M월 d일\na h:mm" )
        return  twoLineFormat.format( this.datetime )

    }


}