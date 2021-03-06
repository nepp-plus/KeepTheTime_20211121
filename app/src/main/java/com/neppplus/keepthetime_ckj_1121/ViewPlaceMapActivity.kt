package com.neppplus.keepthetime_ckj_1121

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
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
//            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext) {
//                override fun getText(p0: InfoWindow): CharSequence {
//                    return mScheduleData.place
//                }
//
//            }

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

            val startingPoint = LatLng(  mScheduleData.startLatitude, mScheduleData.startLongitude  )

//            출발지 좌표 이용 => 출발지 표시 마커 별도 추가

            val startingPointMarker = Marker()
            startingPointMarker.position = startingPoint
            startingPointMarker.icon = OverlayImage.fromResource(R.drawable.red_marker)
            startingPointMarker.map = naverMap


//            오디세이 라이브러리로 -> 대중교통 경로 API 호출

            val myODsayService = ODsayService.init(mContext, resources.getString(R.string.odsay_key))

            myODsayService.requestSearchPubTransPath(
                mScheduleData.startLongitude.toString(),
                mScheduleData.startLatitude.toString(),
                mScheduleData.longitude.toString(),
                mScheduleData.latitude.toString(),
                null,
                null,
                null,
                object : OnResultCallbackListener {
                    override fun onSuccess(p0: ODsayData?, p1: API?) {

                        val jsonObj = p0!!.json

                        Log.d("길찾기-지도상세", jsonObj.toString())

//                        지도에 선 긋기 위한 좌표 목록.

                        val transCoords = ArrayList<LatLng>()

//                        첫 좌표 : 출발지를 등록.
                        transCoords.add( startingPoint )

//                        중간 좌표들 : 대중교통의 정거장 들의 좌표. (API 응답 파싱)

                        val resultObj = jsonObj.getJSONObject("result")

                        val pathArr = resultObj.getJSONArray("path")


//                        만약 추천경로가 안나오면 중간 좌표추가 X
                        if (pathArr.length() > 0 ) {

                            val firstRecommendPath = pathArr.getJSONObject(0)
                            val subPathArr = firstRecommendPath.getJSONArray("subPath")

//                            세부 경로들 하나씩 꺼내보자 -> 정거장 목록이 있는가? 추가 검사

                            for ( i  in  0 until  subPathArr.length() ) {

                                val subPathObj = subPathArr.getJSONObject(i)

//                                정거장 목록이 null이 아닌가? => 내려주는가?

                                if ( !subPathObj.isNull("passStopList") ) {

//                                    실제 정거장 목록 추출 -> transCoords에 추가 등록

                                    val passStopListObj = subPathObj.getJSONObject("passStopList")
                                    val stationsArr = passStopListObj.getJSONArray("stations")

                                    for ( j  in 0 until  stationsArr.length() ) {

                                        val stationObj = stationsArr.getJSONObject(j)

//                                        x : 경도 (lng) ,  y : 위도 (lat)

                                        val lat = stationObj.getString("y").toDouble()
                                        val lng = stationObj.getString("x").toDouble()

//                                        네이버 지도 좌표로 가공
                                        val naverLatLng = LatLng(lat, lng)

//                                        교통 좌표 목록에 추가
                                        transCoords.add(naverLatLng)


                                    }

                                }

                            }

                        }

//                        마지막 좌표 : 도착지를 등록.

                        val endPoint = LatLng( mScheduleData.latitude,  mScheduleData.longitude )
                        transCoords.add( endPoint )

//                        경로는 지도상세화면에서는, 한번만 그려줄 생각.

                        val path = PathOverlay()
                        path.coords = transCoords
                        path.map = naverMap


//                        말풍선의 내용을, 경로찾기가 끝나고 나서 세팅.
//                        커스텀 뷰를 => 말풍선 내에 띄워보자.
//                        네이버 지도 기능 (설명 X) + 안드로이드 코딩 지식 활용 => 응용
                        infoWindow.adapter = object : InfoWindow.DefaultViewAdapter(mContext) {
                            override fun getContentView(p0: InfoWindow): View {

//                                말풍선에 들어갈 xml 그리고 => inflate => getContentView 함수의 결과로 지정.

                                val view = LayoutInflater.from(mContext).inflate(R.layout.place_info_window_view, null)

                                val txtPlaceName = view.findViewById<TextView>(R.id.txtPlaceName)
                                txtPlaceName.text = mScheduleData.place

                                val txtRequireTime = view.findViewById<TextView>(R.id.txtRequireTime)

//                                길찾기 API가 알려주는 (jsonObj안에 저장됨) 예상시간 (추가 파싱)을 가지고 문구로 설정.
//                                모든 경로중 0번째 경로의 도착 소요 시간 파싱.

                                val firstPath = pathArr.getJSONObject(0)

//                                첫 경로의 정보 사항
                                val infoObj = firstPath.getJSONObject("info")
                                val totalTime = infoObj.getInt("totalTime")

//                                60분 넘느냐 아니냐 로 따로 문구 세팅.

                                if (totalTime >= 60) {

//                                    1시간 이상 => ?시간 ?분 소요
//                                      150분 : 2시간 30분

                                    val hour = totalTime / 60  // Int / Int는 무조건 정수로 나옴. => 몫을 구해주는 효과.
                                    val minute = totalTime % 60

                                    txtRequireTime.text = "${hour}시간 ${minute}분 소요"

                                }
                                else {

//                                    1시간 이내 => 분만 표기.
                                    txtRequireTime.text = "${totalTime}분 소요"

                                }

//                                txtRequireTime.text = ?

                                return view

                            }

                        }


                    }

                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }


                }
            )







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