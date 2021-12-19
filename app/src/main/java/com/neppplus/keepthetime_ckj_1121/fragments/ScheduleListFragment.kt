package com.neppplus.keepthetime_ckj_1121.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.keepthetime_ckj_1121.EditAppointmentActivity
import com.neppplus.keepthetime_ckj_1121.R
import com.neppplus.keepthetime_ckj_1121.adatpers.ScheduleRecyclerAdapter
import com.neppplus.keepthetime_ckj_1121.databinding.FragmentScheduleListBinding
import com.neppplus.keepthetime_ckj_1121.datas.BasicResponse
import com.neppplus.keepthetime_ckj_1121.datas.ScheduleData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleListFragment : BaseFragment() {

    lateinit var binding: FragmentScheduleListBinding

    val mScheduleList = ArrayList<ScheduleData>()
    lateinit var mScheduleAdapter : ScheduleRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnAddAppointment.setOnClickListener {

            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        mScheduleAdapter = ScheduleRecyclerAdapter( mContext, mScheduleList )
        binding.appointmentRecyclerView.adapter = mScheduleAdapter
        binding.appointmentRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

//    생명주기 - onResume 이 화면으로 돌아올때마다 실행되는 함수.

    override fun onResume() {
        super.onResume()

        getScheduleListFromServer()

    }

    fun getScheduleListFromServer() {

        apiService.getRequestAppointment().enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mScheduleList.clear()
                    mScheduleList.addAll( br.data.appointments )

                    mScheduleAdapter.notifyDataSetChanged()


                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        } )

    }


}