package com.beombeom.composeex.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(application : Application) : AndroidViewModel(application){

    val toast = MutableStateFlow("")
}