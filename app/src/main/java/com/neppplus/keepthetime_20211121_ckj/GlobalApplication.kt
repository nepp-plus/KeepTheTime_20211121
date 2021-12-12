package com.neppplus.keepthetime_20211121_ckj

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "5c799789a1fa90febcf55361100a1fea")

    }

}