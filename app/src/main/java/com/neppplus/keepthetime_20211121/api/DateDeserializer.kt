package com.neppplus.keepthetime_20211121.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer : JsonDeserializer<Date> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {

//        서버에서 받은 항목중, Date로 파싱하려고 하는 항목을 일단 String으로 받아오자.
        val dateStr = json?.asString

//        String -> Date형태로 변환에 필요한 양식.
        val serverFormat = SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" )

//        dateStr (String) => Date 형태로 변환.

        val date = serverFormat.parse( dateStr )!!

        return date

    }

}