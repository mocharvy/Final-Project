package com.programmer.finalproject.ui.fragment.kursus

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    private val _filterLiveData = MutableLiveData<Triple<String, String, String>>()
    val filterLiveData: LiveData<Triple<String, String, String>> = _filterLiveData

    fun setFilterData(recFilter: String, categoryFilter: String, levelFilter: String) {
        _filterLiveData.value = Triple(recFilter, categoryFilter, levelFilter)
    }
}