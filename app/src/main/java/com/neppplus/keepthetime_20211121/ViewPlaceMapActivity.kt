package com.neppplus.keepthetime_20211121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20211121.databinding.ActivityViewPlaceMapBinding

class ViewPlaceMapActivity : BaseActivity() {

    lateinit var binding: ActivityViewPlaceMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_place_map)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}