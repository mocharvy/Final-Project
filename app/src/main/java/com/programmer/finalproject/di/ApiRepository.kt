package com.programmer.finalproject.di

import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {
}