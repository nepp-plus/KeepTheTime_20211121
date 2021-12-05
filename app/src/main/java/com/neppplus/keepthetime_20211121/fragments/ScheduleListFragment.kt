package com.neppplus.keepthetime_20211121.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neppplus.keepthetime_20211121.EditAppointmentActivity
import com.neppplus.keepthetime_20211121.R
import com.neppplus.keepthetime_20211121.databinding.FragmentScheduleListBinding
import com.neppplus.keepthetime_20211121.datas.BasicResponse
import com.neppplus.keepthetime_20211121.datas.ScheduleData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleListFragment : BaseFragment() {

    lateinit var binding: FragmentScheduleListBinding

    val mScheduleList = ArrayList<ScheduleData>()

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

        getScheduleListFromServer()
    }

    fun getScheduleListFromServer() {

        apiService.getRequestAppointment().enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mScheduleList.clear()
                    mScheduleList.addAll( br.data.appointments )


                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        } )

    }


}