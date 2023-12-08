package com.programmer.finalproject.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmer.finalproject.di.ApiRepository
import com.programmer.finalproject.model.user.update.ProfileResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class EditProfileFragmentViewModel @Inject constructor(
//    private val userRepository: UserRepository,
    private val apiRepository: ApiRepository
) : ViewModel() {



    val _updateResponse = MutableLiveData<ProfileResponse>()
    val updateResponse: LiveData<ProfileResponse>
        get() = _updateResponse



    fun uploadProfilePic(name: RequestBody, email: RequestBody, phone_number: RequestBody, country: RequestBody, city: RequestBody, photo: MultipartBody.Part, token:String){
        viewModelScope.launch {
            _updateResponse.postValue(apiRepository.editProfile(name,email,phone_number,country,city,photo,token))
        }
    }

}
