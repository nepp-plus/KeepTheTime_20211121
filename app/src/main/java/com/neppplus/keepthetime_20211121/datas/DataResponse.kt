package com.neppplus.keepthetime_20211121.datas

class DataResponse(
    var user: UserData,
    var token : String,

//    친구목록 API의 파싱에만 사용
    var friends : List<UserData>,

    var users: List<UserData>

) {
}