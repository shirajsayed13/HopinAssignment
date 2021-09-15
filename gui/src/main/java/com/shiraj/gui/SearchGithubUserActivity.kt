package com.shiraj.gui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.shiraj.base.activity.BaseActivity
import com.shiraj.gui.databinding.ActivitySearchGithubUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SearchGithubUserActivity : BaseActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_search_github_user

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = ActivitySearchGithubUserBinding::inflate

    override val binding: ActivitySearchGithubUserBinding
        get() = super.binding as ActivitySearchGithubUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}