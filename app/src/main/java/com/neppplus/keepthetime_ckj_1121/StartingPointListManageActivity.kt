package com.neppplus.keepthetime_ckj_1121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_ckj_1121.databinding.ActivityStartingPointListManageBinding

class StartingPointListManageActivity : BaseActivity() {

    lateinit var binding: ActivityStartingPointListManageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_starting_point_list_manage)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}