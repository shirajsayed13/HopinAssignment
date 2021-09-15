package com.shiraj.gui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SearchFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_search

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    override val binding: FragmentSearchBinding
        get() = super.binding as FragmentSearchBinding

    override fun onInitView() {
        binding.apply {

            btnSubmit.setOnClickListener {
                val searchKeyword = binding.edtLogin.text.toString().trim()
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
                        searchKeyword
                    )
                )
            }

            edtLogin.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val searchKeyword = binding.edtLogin.text.toString().trim()
                    findNavController().navigate(
                        SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
                            searchKeyword
                        )
                    )
                    return@OnEditorActionListener true
                }
                false
            })
        }
    }
}