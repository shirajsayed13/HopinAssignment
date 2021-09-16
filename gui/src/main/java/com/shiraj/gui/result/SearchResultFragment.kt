package com.shiraj.gui.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.shiraj.base.failure
import com.shiraj.base.fragment.BaseFragment
import com.shiraj.core.WebServiceFailure
import com.shiraj.gui.AppToast
import com.shiraj.gui.R
import com.shiraj.gui.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
internal class SearchResultFragment : BaseFragment() {

    override val layoutResId: Int
        get() = R.layout.fragment_search_result

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding
        get() = FragmentSearchResultBinding::inflate

    override val binding: FragmentSearchResultBinding
        get() = super.binding as FragmentSearchResultBinding


    private val viewModel: SearchResultViewModel by viewModels()

    private lateinit var searchResultAdapter: SearchResultAdapter

    private val args: SearchResultFragmentArgs by navArgs()

    override fun onInitView() {
        viewModel.apply {
            failure(failure, ::handleFailure)
            viewModel.keyword = args.searchKeyword
        }

        searchResultAdapter = SearchResultAdapter()
        binding.apply {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            rvItems.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = searchResultAdapter
            }

            searchResultAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached
                    && searchResultAdapter.itemCount < 1
                ) {
                    rvItems.isVisible = false
                    tvNoSearchResult.isVisible = true
                } else {
                    showSearchResult()
                    rvItems.isVisible = true
                    tvNoSearchResult.isVisible = false
                }
            }
        }
    }

    private fun showSearchResult() {
        binding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
        }

        lifecycleScope.launch {
            viewModel.searchResults.collectLatest {
                searchResultAdapter.submitData(it)
            }
        }
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