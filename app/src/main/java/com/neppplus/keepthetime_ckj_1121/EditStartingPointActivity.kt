package com.neppplus.keepthetime_ckj_1121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker
import com.neppplus.keepthetime_ckj_1121.databinding.ActivityEditStartingPointBinding

class EditStartingPointActivity : BaseActivity() {

    lateinit var binding: ActivityEditStartingPointBinding

    var mSelectedMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_starting_point)
        binding.mapView.onCreate(savedInstanceState)
        setupEvents()
        setValues()
    }



    override fun setupEvents() {

    }

    override fun setValues() {

        binding.mapView.getMapAsync {

            val naverMap = it

//            지도의 한 곳을 클릭하면 => 마커를 추가

            naverMap.setOnMapClickListener { pointF, latLng ->

//                클릭 될때마다 생성자 호출 => 매번 새 마커 그려주기.
//                단 하나의 마커만 유지하자. => 아직 안그려졌을때만 생성하자.
//                위치는 매번 클릭될때마다 설정하자. (EditAppointmentActivity 참고)

                if (mSelectedMarker == null) {
//                    멤버변수로 만들어둔 마커가 null일때만 생성. => 하나의 객체를 유지.
                    mSelectedMarker = Marker()
                }

                mSelectedMarker!!.position = latLng
                mSelectedMarker!!.map =  naverMap


//                클릭한 위치 (latLng)로 카메라 이동 => 마커가 가운데 위치.

                val cameraUpdate = CameraUpdate.scrollTo( latLng )
                naverMap.moveCamera( cameraUpdate )


            }

        }

    }


    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

}