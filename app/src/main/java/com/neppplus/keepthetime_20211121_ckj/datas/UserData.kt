package com.neppplus.keepthetime_20211121_ckj.datas

import com.google.gson.annotations.SerializedName

class UserData(

    var id: Int,
    var provider: String,
    var uid: String?,
    var email: String,
    @SerializedName("ready_minute")
    var readyMinute: Int,
    @SerializedName("nick_name")
    var nickname : String,
    @SerializedName("profile_img")
    var profileImageURL : String

) {
}