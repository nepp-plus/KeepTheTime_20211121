package com.neppplus.keepthetime_ckj_1121.adatpers

import android.content.Context
import android.widget.ArrayAdapter
import com.neppplus.keepthetime_ckj_1121.datas.PlaceData

class StartingPointSpinnerAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<PlaceData>
) : ArrayAdapter<PlaceData>(mContext, resId, mList) {



}