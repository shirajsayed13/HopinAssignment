package com.shiraj.gui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.shiraj.base.failure
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.base.observe
import com.shiraj.core.WebServiceFailure
import com.shiraj.core.model.GithubUserModel
import com.shiraj.gui.AppToast
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
internal class SearchResultFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_search_result

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentSearchResultBinding::inflate

    override val binding: FragmentSearchResultBinding
        get() = super.binding as FragmentSearchResultBinding


    private val viewModel: SearchResultViewModel by viewModels()

    @Inject
    lateinit var searchResultAdapter: SearchResultAdapter

    override fun onInitView() {
        viewModel.apply {
            failure(failure, ::handleFailure)
            observe(userItems, ::showSearchResult)
            loadSearchResult()
        }

        binding.apply {
            rvItems.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = searchResultAdapter
            }
        }
    }

    private fun showSearchResult(userItems: List<GithubUserModel.Item>) {
        searchResultAdapter.items = userItems
    }


    private fun handleFailure(e: Exception?) {
        Timber.v("handleFailure: IN")
        Timber.e(e)
        when (e) {
            is WebServiceFailure.NoNetworkFailure -> showErrorToast("No internet connection!")
            is WebServiceFailure.NetworkTimeOutFailure, is WebServiceFailure.NetworkDataFailure -> showErrorToast(
                "Internal server error!"
            )
            else -> showErrorToast("Unknown error occurred!")
        }
        Timber.v("handleFailure: OUT")
    }

    private fun Fragment.showErrorToast(msg: String) {
        AppToast.show(requireContext(), msg, Toast.LENGTH_SHORT)
    }
}