package com.beombeom.composeex.presentation.examples.lazyLow

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel


class RecyclerViewViewModel(application: Application) : AndroidViewModel(application) {
    val items = mutableStateOf<List<RecyclerViewEntity>>(generateDummyItems())
    private fun generateDummyItems(): List<RecyclerViewEntity> {

        return List(500) { index ->
            RecyclerViewEntity(
                id = index
            )
        }
    }
}