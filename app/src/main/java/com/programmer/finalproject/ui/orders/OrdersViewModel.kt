package com.programmer.finalproject.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.payment.HistoryPaymentResponse
import com.programmer.finalproject.model.payment.OrderRequest
import com.programmer.finalproject.model.payment.OrderResponse
import com.programmer.finalproject.model.payment.order.PutOrderRequest
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


    val _getListHistoryPayment= MutableLiveData<HistoryPaymentResponse?>()

    val getListHistoryPayment: LiveData<HistoryPaymentResponse?>
        get() = _getListHistoryPayment



    fun getHistoryPayment(token:String) {
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.getHistoryPayment(token).enqueue(
            object : Callback<HistoryPaymentResponse> {
                override fun onFailure(call: Call<HistoryPaymentResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<HistoryPaymentResponse>,
                    response: Response<HistoryPaymentResponse>
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

    fun  orderCourses(token: String,orderRequest: OrderRequest){
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.orderCourses(token,orderRequest).enqueue(
            object : Callback<OrderResponse> {
                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {

                    viewModelScope.launch {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            isError.postValue(true)
                        } else {
                            isError.postValue(false)
                        }
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }

    fun  putOrder(token: String,order_id:String,putOrderRequest: PutOrderRequest){
        loadingState.postValue(true)
        errorState.postValue(Pair(false, null))
        apiRepository.putOrder(token,order_id,putOrderRequest).enqueue(
            object : Callback<OrderResponse> {
                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    viewModelScope.launch {
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }

                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {

                    viewModelScope.launch {
                        if (response.code() == 400 || response.code() == 401 || response.code() == 500) {
                            isError.postValue(true)
                        } else {
                            isError.postValue(false)
                        }
                        loadingState.postValue(false)
                        errorState.postValue(Pair(false, null))
                    }
                }
            }
        )
    }



}