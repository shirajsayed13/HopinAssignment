package com.shiraj.gui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiraj.base.viewmodel.BaseViewModel
import com.shiraj.core.model.GithubUserModel
import com.shiraj.core.usecase.GetGithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class SearchResultViewModel @Inject constructor(
    private val getGithubUserUseCase: GetGithubUserUseCase
) : BaseViewModel() {

    private val _userItems: MutableLiveData<List<GithubUserModel.Item>> by lazy { MutableLiveData() }
    internal val userItems: LiveData<List<GithubUserModel.Item>> = _userItems

    internal fun loadSearchResult() {
        Timber.d("loadSearchResult: ")
        launchUseCase {
            _userItems.postValue(getGithubUserUseCase())
        }
    }
}