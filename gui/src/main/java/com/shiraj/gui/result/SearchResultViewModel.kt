package com.shiraj.gui.result

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shiraj.base.viewmodel.BaseViewModel
import com.shiraj.core.model.GithubUserModel
import com.shiraj.core.usecase.GetGithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class SearchResultViewModel @Inject constructor(
    private val getGithubUserUseCase: GetGithubUserUseCase
) : BaseViewModel() {

    internal lateinit var keyword: String

    val searchResults: Flow<PagingData<GithubUserModel.Item>> = Pager(
        config = PagingConfig(
            10,
            enablePlaceholders = false,
            prefetchDistance = 10
        )
    ) {
        SearchResultPageSource(getGithubUserUseCase, keyword)
    }.flow
}