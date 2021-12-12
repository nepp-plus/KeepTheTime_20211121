package com.neppplus.keepthetime_ckj_1121

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.neppplus.keepthetime_ckj_1121.databinding.ActivityViewPlaceMapBinding
import com.neppplus.keepthetime_ckj_1121.datas.ScheduleData
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener

class ViewPlaceMapActivity : BaseActivity() {

    lateinit var binding: ActivityViewPlaceMapBinding

    lateinit var mScheduleData : ScheduleData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_place_map)
        binding.naverMapView.onCreate(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mScheduleData = intent.getSerializableExtra("schedule") as ScheduleData

//        0. 프로젝트에 네이버지도 설치 (완료)

//        1. 화면 (xml) 에 네이버 맵 띄워주기


//        2. 네이버 맵 객체를 실제로 얻어내기 -> getMapAsync

        binding.naverMapView.getMapAsync {

            val naverMap = it

//        3. 카메라 이동 / 마커 추가  (받아온 스케쥴의 위도/경도 이용)

//            위치 (좌표) 데이터 객체

            val coord =  LatLng( mScheduleData.latitude,  mScheduleData.longitude )

            val cameraUpdate = CameraUpdate.scrollTo( coord )
            naverMap.moveCamera( cameraUpdate )

            val marker = Marker()
            marker.position = coord
            marker.map = naverMap

//            추가기능 체험 - 정보창 (말풍선) => 마커에 반영

            val infoWindow = InfoWindow()
            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext) {
                override fun getText(p0: InfoWindow): CharSequence {
                    return mScheduleData.place
                }

            }

            infoWindow.open(marker)

            naverMap.setOnMapClickListener { pointF, latLng ->

//                지도 아무데나 클릭하면, 정보창 닫기
                infoWindow.close()

            }


            marker.setOnClickListener {

                if (marker.infoWindow == null) {
//                    정보창이 안열려있는 상태.

                    infoWindow.open(marker)

                }
                else {
//                    이미 정보창이 열린 상태 => 닫아주자.

                    infoWindow.close()

                }


                return@setOnClickListener true
            }

//            ODsay 라이브러리 재활용 -> JSON 파싱 => 출발지 ~ 도착지 대중교통 경로 지도에 표시.
//            복잡한 모양의 JSONObject 직접 파싱 복습 => ~ 12:30분

//            출발지 정보 ~ 도착지 API 호출
//            or 본인 집 좌표 직접 출발지 지정

            val startingPoint = LatLng(  37.61324219259818, 126.92932036898978  )

//            오디세이 라이브러리로 -> 대중교통 경로 API 호출

            val myODsayService = ODsayService.init(mContext, resources.getString(R.string.odsay_key))

            myODsayService.requestSearchPubTransPath(
                126.92932036898978.toString(),
                37.61324219259818.toString(),
                mScheduleData.longitude.toString(),
                mScheduleData.latitude.toString(),
                null,
                null,
                null,
                object : OnResultCallbackListener {
                    override fun onSuccess(p0: ODsayData?, p1: API?) {

                    }

                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }


                }
            )


            val endPoint = LatLng( mScheduleData.latitude,  mScheduleData.longitude )




        }




    }

    override fun onStart() {
        super.onStart()
        binding.naverMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.naverMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.naverMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.naverMapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.naverMapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.naverMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.naverMapView.onLowMemory()
    }

}