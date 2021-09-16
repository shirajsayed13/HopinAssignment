package com.shiraj.gui

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.shiraj.base.activity.BaseActivity
import com.shiraj.gui.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class TestActivity : BaseActivity() {
    override val layoutResId: Int get() = R.layout.activity_test
    override val bindingInflater: (LayoutInflater) -> ViewBinding get() = ActivityTestBinding::inflate
}