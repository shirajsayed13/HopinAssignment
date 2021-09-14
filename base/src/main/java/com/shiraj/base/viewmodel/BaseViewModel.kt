package com.shiraj.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    val _failure: MutableLiveData<Exception> by lazy { MutableLiveData() }

    val failure: LiveData<Exception> get() = _failure

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared: ")
    }

    protected fun launchUseCase(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                block()
            } catch (e: Exception) {
                _failure.postValue(e)
            }
        }
    }
}