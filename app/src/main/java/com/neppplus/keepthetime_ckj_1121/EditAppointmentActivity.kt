package com.neppplus.keepthetime_ckj_1121

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.PathOverlay
import com.neppplus.keepthetime_ckj_1121.databinding.ActivityEditAppointmentBinding
import com.neppplus.keepthetime_ckj_1121.datas.BasicResponse
import com.neppplus.keepthetime_ckj_1121.datas.PlaceData
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.ln

class EditAppointmentActivity : BaseActivity() {

//    약속을 잡을 일시를 저장할 변수 (Calendar)

    val mSelectedDateTime = Calendar.getInstance()  // 기본값 : 현재 일시

//    약속을 잡을 위치를 저장할 변수 (네이버 - LatLng)
//    그 위치를 보여줄 마커 (네이버 - Marker)
//    처음 화면이 나타날때는, 아직 선택 안한상태. => 위치도 / 마커도 아직 없다. (초기 값 - null)

    var mSelectedLatLng: LatLng? = null
    var mSelectedMarker: Marker? = null

    var mPath: PathOverlay? = null


    lateinit var binding: ActivityEditAppointmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSearchPlace.setOnClickListener {

            val inputPlace = binding.edtPlace.text.toString()

//            장소 이름 => 위/경도 좌표로 변환. => 이런 기능의 API가 있는가?

//            UI / 앱 기능 : 라이브러리 (지도, 소셜로그인)

//            문구 => 다른 데이터 : Open API (장소 이름 => 위/경도 좌표)
//            카카오 장소검색 API 활용.

//            OkHttp라이브러리를 단발성으로 사용하는게 더 편할것으로 보임.
//            retrofit 라이브러리에 내장된 OkHttp 라이브러리 활용.

//            1. 어느 주소로 가야하는가? URL
//            2. 어떤 파라미터? query => URL을 만들때 같이 만들자.

            val url = HttpUrl.parse("https://dapi.kakao.com/v2/local/search/keyword.json")!!.newBuilder()
            url.addEncodedQueryParameter("query", inputPlace)

            val urlString = url.toString()
            Log.d("카카오장소검색주소", urlString)


//            3. 1+2+메쏘드+헤더 종합 => Request 만들기
            val request = Request.Builder()
                .url( urlString )
                .get()
                .header("Authorization", "KakaoAK ${resources.getString(R.string.kakao_api_key)}")
                .build()

//            4. OkHttpClient를 이용해 실제 카카오 서버 호출

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: okhttp3.Call, e: IOException) {
//                    연결 실패
                }

                override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
//                    검색 결과 돌아옴 => 분석 (JSON 파싱) / UI 반영

                    val jsonObj =  JSONObject( response.body()!!.string() )

                    Log.d("검색결과응답", jsonObj.toString())

                    val documentsArr = jsonObj.getJSONArray("documents")

//                    장소목록을 ArrayList에 담아두자.

                    val placeList = ArrayList<PlaceData>()

                    for (i  in 0 until  documentsArr.length()) {

                        val documentObj  =  documentsArr.getJSONObject(i)

                        Log.d("검색결과아이템",  documentObj.toString())

                        val placeName = documentObj.getString("place_name")
                        val lat = documentObj.getString("y").toDouble()
                        val lng = documentObj.getString("x").toDouble()

                        val placeData = PlaceData( placeName, lat, lng )
                        placeList.add(placeData)

                    }

//                    검색 결과로 찾아낸 장소 목록이 전부 추가됨. => UI에서 활용?

                    runOnUiThread {

//                        임시 - 첫 장소의 이름을 토스트
//                        Toast.makeText(mContext, placeList[0].placeName, Toast.LENGTH_SHORT).show()

//                        AlertDialog를 띄우자. => 커스텀 뷰를 가진 AlertDialog.
//                        커스텀뷰의 내용 : RecyclerView를 내용물로. => 장소 목록 띄우기.

                        val alert = AlertDialog.Builder(mContext)

                        val customView = LayoutInflater.from(mContext).inflate(R.layout.my_custom_alert_select_place, null)

                        alert.setTitle("약속 장소 선택")
//                        alert.setMessage("정말 ~~ ?")


                        alert.show()

                    }


                }

            })


        }

        binding.txtTime.setOnClickListener {

//            시간 선택  팝업 (TimePickerDialog) 사용 예시

//            1. 선택 완료시 할 일 (OnTimeSetListener) 설정 -> 변수에 담아두자.

            val timeSetListener = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {

//                    Log.d("선택된시간", "${hourOfDay}시, ${minute}분")

//                    선택한 시간도 실제로 저장
                    mSelectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    mSelectedDateTime.set(Calendar.MINUTE, minute)

//                    선택한 시간을 -> "오후 4:05"   형태로 txtTime에 표시
//                    SimpleDateFormat 활용.

                    val timeFormat = SimpleDateFormat("a h:mm")
                    val timeStr = timeFormat.format(mSelectedDateTime.time)

                    binding.txtTime.text = timeStr


                }
            }

//            2. 시간 선택 팝업 출현

            val timePicker = TimePickerDialog(
                mContext,
                timeSetListener,
                15,
                30,
                false
            )

            timePicker.show()

        }

        binding.txtDate.setOnClickListener {

//            날짜 선택 팝업  (DatePickerDialog) 사용 예시

//            선택 완료시 할 일 (JAVA - Interface) 설정 => 변수에 담아두자

            val dateSetListener = object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

//                    실제로 날짜가 선택되면 할 일 적는 공간.

//                    Log.d("선택한년월일", "${year}년  ${month}월 ${dayOfMonth}일 선택됨")

//                    선택된 일시를 저장할 변수에, 연/월/일을 저장.

//                    mSelectedDateTime.set( Calendar.YEAR, year )
//                    mSelectedDateTime.set( Calendar.MONTH, month )
//                    mSelectedDateTime.set( Calendar.DAY_OF_MONTH, dayOfMonth )

//                    년/월/일을 한번에 저장하는 set 함수 활용
                    mSelectedDateTime.set(year, month, dayOfMonth)

//                    txtDate 의 문구를 -> 21년 8월 5일 과 같은 양식으로 가공해서 텍스트 세팅.

                    val dateFormat = SimpleDateFormat("yy년 M월 d일")
                    val dateStr = dateFormat.format(mSelectedDateTime.time)

                    binding.txtDate.text = dateStr

                }

            }

//            실제 팝업창 띄우기

//            Kotlin : JAVA 기반 언어 => 월 : 0 ~ 11로 만들어져있음.

//            오늘 날짜를 기본으로 띄우도록. => mSelectedDateTime에 저장된 값 활용
//            선택한 이후로는 => 이전에 선택한 날짜가 뜨도록.

            val datePickerDialog = DatePickerDialog(
                mContext,
                dateSetListener,
                mSelectedDateTime.get(Calendar.YEAR),
                mSelectedDateTime.get(Calendar.MONTH),
                mSelectedDateTime.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()


        }

        binding.btnOk.setOnClickListener {

//            입력값 검증. (vaildation)

//            1. 일자 / 시간을 모두 선택했는지?

            if (binding.txtDate.text == "날짜 선택" || binding.txtTime.text == "시간 선택") {

//                둘중 하나를 아직 입력하지 않은 상황.
                Toast.makeText(mContext, "약속 일시를 모두 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

//            2. 선택된 일시 (mSelectedDateTime)가, 현재 시간보다 더 나중 시간인가?
//              => 과거의 약속이라면 앱에서 등록 거부.

//            현재시간을 변수에 저장
            val now = Calendar.getInstance()  // 현재 시간 (클릭된 시간)을 기록

//            두개의 시간을 양으로 변환해서 대소비교.

            if (mSelectedDateTime.timeInMillis < now.timeInMillis) {

//                약속시간이, 현재시간보다 덜 시간이 흐른 상태. (더 이전 시간)

//                약속시간은 지금보다 미래여야 의미가 있다.

                Toast.makeText(mContext, "약속시간은 더 미래의 시간으로 설정해주세요.", Toast.LENGTH_SHORT).show()

//                이 함수를 강제종료. (서버에 데이터 보내는걸 취소)
                return@setOnClickListener

            }

//            약속 장소 추가 검증

            if (mSelectedLatLng == null) {
                Toast.makeText(mContext, "약속 장소를 지도에서 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputTitle = binding.edtTitle.text.toString()
//            val inputDateTime = binding.edtDateTime.text.toString()

//            mSelectedDateTime에 저장된 약속일시를 => String으로 가공 (SimpleDateFormat) => 서버에 첨부.

            val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val finalDateTimeStr = serverFormat.format(mSelectedDateTime.time)

            val inputPlace = binding.edtPlace.text.toString()

            apiService.postRequestAppointment(
                inputTitle,
                finalDateTimeStr,
                inputPlace,
                mSelectedLatLng!!.latitude,
                mSelectedLatLng!!.longitude
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

    override fun setValues() {

        binding.naverMapView.getMapAsync {

//            로딩이 끝난 네이버맵 객체(인스턴스가) => it 변수에 담겨있다.
            val naverMap = it

//            기능 :  지도를 클릭하면 -> 클릭된 지점에 마커 찍기. (커스텀 마커 예시)

            naverMap.setOnMapClickListener { point, latLng ->

//                클릭된 좌표 latLng -> 카메라 이동 (정가운데) / 마커 찍기

                val cameraUpdate = CameraUpdate.scrollTo(latLng)
                naverMap.moveCamera(cameraUpdate)

//                선택 한 위치를 멤버변수에 담아두자.
                mSelectedLatLng = latLng

//                선택한 위치를 보여줄 마커도 (만들어진게 없다면 새로) 생성.
                if (mSelectedMarker == null) {
                    mSelectedMarker = Marker()
                }

                mSelectedMarker!!.position = latLng
                mSelectedMarker!!.map = naverMap


//                하나의 지점 (본인 집-startingPoint) 에서 -> 클릭한 지점 (latLng) 까지 선 긋기.

                val startingPoint = LatLng(37.61275604060842, 126.9300471949015)

//                출발지 ~ 도착지까지의 대중교통 정거장 목록을 위경도 추출.
//                ODSay 라이브러리 설치 => API 활용.

                val myODsayService =
                    ODsayService.init(mContext, resources.getString(R.string.odsay_key))

                myODsayService.requestSearchPubTransPath(
                    startingPoint.longitude.toString(),
                    startingPoint.latitude.toString(),
                    latLng.longitude.toString(),
                    latLng.latitude.toString(),
                    null,
                    null,
                    null,

                    object : OnResultCallbackListener {
                        override fun onSuccess(p0: ODsayData?, p1: API?) {
                            val jsonObj = p0!!.json
                            Log.d("길찾기응답", jsonObj.toString())

//                            출발지 ~ 지하철역 (or 버스정거장) 좌표들 ~ 도착지 좌표 목록으로 설정.

                            val transCoords = ArrayList<LatLng>()

//                            출발지를 첫 좌표로 등록
                            transCoords.add(startingPoint)

//                            지하철 역 등 좌표들 등록 (파싱 - 반복)

                            val resultObj = jsonObj.getJSONObject("result")

                            val pathArr = resultObj.getJSONArray("path")

//                            첫번째 경로만 활용 예정.
                            if (pathArr.length() > 0) {

                                val firstPath = pathArr.getJSONObject(0)
                                Log.d("첫번째추천경로", firstPath.toString())

                                val subPathArr = firstPath.getJSONArray("subPath")

//                                모든 세부경로 반복 파싱
                                for ( i  in  0  until  subPathArr.length()) {

                                    val subPathObj = subPathArr.getJSONObject(i)

//                                    정거장 목록 - passStopList가 있을때만 내부 파싱.

                                    if ( !subPathObj.isNull("passStopList") ) {

                                        val passStopListObj = subPathObj.getJSONObject("passStopList")

                                        val stationsArr = passStopListObj.getJSONArray("stations")

                                        Log.d("정거장목록", stationsArr.toString())

                                        for ( j in   0 until  stationsArr.length()  ) {
                                            val stationObj = stationsArr.getJSONObject(j)

//                                            정거장의 x, y => 지도 표시 경도(lng) , 위도(lat)

                                            val lat = stationObj.getString("y").toDouble()
                                            val lng = stationObj.getString("x").toDouble()

//                                            네이버 지도에서 사용할 위치 객체로 변환
                                            val stationLatLng = LatLng( lat, lng )

//                                            경로에서 -> 표시할 중간 좌표로 추가 등록.

                                            transCoords.add(stationLatLng)


                                        }

                                    }


                                }

                            }


//                            도착지를 마지막 좌표로 등록
                            transCoords.add(latLng)

//                            지도에 선 그려주기

//                           선이 그어질 경로 (여러 지점의 연결로 표현)

//                           PathOverlay() 선 긋는 객체 생성. => 지도에 클릭될때마다 새로 생성됨. => 선도 하나씩 새로 그어짐.
//                           val path = PathOverlay()

//                            mPath 변수가 null 상태라면? 새 객체 만들어서 채워줌
                            if (mPath == null) {
                                mPath = PathOverlay()
                            }

                            mPath!!.coords = transCoords

                            mPath!!.map = naverMap


                        }

                        override fun onError(p0: Int, p1: String?, p2: API?) {

                            Log.d("길찾기에러", p1.toString())

                        }


                    }

                )


            }


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