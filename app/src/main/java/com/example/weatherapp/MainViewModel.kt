package com.example.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.Adapters.ViewAdaptDataClass

class MainViewModel: ViewModel() {
    val CurrentData = MutableLiveData<ViewAdaptDataClass>()
    val DataLsit = MutableLiveData<List<ViewAdaptDataClass>>()
}