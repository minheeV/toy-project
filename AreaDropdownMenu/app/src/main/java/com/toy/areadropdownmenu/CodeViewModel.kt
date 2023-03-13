package com.toy.areadropdownmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodeViewModel : ViewModel() {
    private val _firstCode = MutableLiveData("0")
    val firstCode: LiveData<String> = _firstCode

    private val _secondCode = MutableLiveData("0")
    val secondCode: LiveData<String> = _secondCode

    fun setFirstCode(code: String) {
        _firstCode.value = code
    }

    fun setSecondCode(code: String) {
        _secondCode.value = code
    }

}