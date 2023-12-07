package com.programmer.finalproject.ui.fragment.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Authorization(
    val token: String,
    val isLogin: Boolean
) : Parcelable