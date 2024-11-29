package com.beombeom.composeex.presentation.examples.recyclerView

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel


class HorizontalRecyclerViewViewModel(application: Application) : AndroidViewModel(application) {
    val items = mutableStateOf<List<RecyclerViewEntity>>(generateDummyProfiles())
    private fun generateDummyProfiles(): List<RecyclerViewEntity> {

        return List(500) { index ->
            RecyclerViewEntity(
                id = index
            )
        }
    }
}