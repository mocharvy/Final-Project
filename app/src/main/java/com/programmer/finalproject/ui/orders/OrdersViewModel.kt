package com.programmer.finalproject.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.courses.CategoryResponse
import com.programmer.finalproject.model.courses.CoursesResponse
import com.programmer.finalproject.model.payment.OrdersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {
    val loadingState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Pair<Boolean, Exception?>>()
    var isError = MutableLiveData<Boolean>()


    val _getListHistoryPayment= MutableLiveData<OrdersResponse?>()

    val getListHistoryPayment: LiveData<OrdersResponse?>
        get() = _getListHistoryPayment


    fun getHistoryPayment(token:String) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.getHistoryPayment(token).enqueue(
            object : Callback<OrdersResponse> {
                override fun onFailure(call: Call<OrdersResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<OrdersResponse>,
                    response: Response<OrdersResponse>
                ) {

                    viewModelScope.launch {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            isError.postValue(true)
                        } else {
                            isError.postValue(false)
                            _getListHistoryPayment.postValue(response.body())

                        }
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

}