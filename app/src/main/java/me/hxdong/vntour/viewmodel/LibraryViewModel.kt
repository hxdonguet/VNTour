package me.hxdong.vntour.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hxdong.vntour.EventBus
import me.hxdong.vntour.VNtourEvent
import me.hxdong.vntour.data.Tourism
import me.hxdong.vntour.repository.TourismRepository

enum class LibraryState {
    Loading, Done
}

class LibraryViewModel constructor(private val tourismRepository: TourismRepository) : ViewModel() {

    val state = mutableStateOf(LibraryState.Loading)
    val visibleTours = mutableStateListOf<Tourism>()

    init {
        Log.d("LibraryViewModel", "Init")
        loadData()
        viewModelScope.launch {
            EventBus.events.collect { event ->
                Log.d("LibraryViewModel", "Handle event: ${event.name}")
                when (event) {

                    VNtourEvent.FAVORITE_CHANGED, VNtourEvent.SUBSCRIBED_CHANGED -> {
                        state.value = LibraryState.Loading
                        loadData()
                    }

                    else -> {
                        Log.d("LibraryViewModel", "Not handled event: ${event.name}")
                    }
                }
            }
        }
    }

    private fun loadData() {
        Log.d("LibraryViewModel", "Load data")
        visibleTours.clear()
        viewModelScope.launch(Dispatchers.IO) {
            val matches = tourismRepository.findTourismByPredicate { it.isSubscribed }

            withContext(Dispatchers.Main) {
                visibleTours.addAll(matches)
            }
        }
        state.value = LibraryState.Done
    }

    private fun isFavorite(tourism: Tourism) = tourism.isFavorite
    private fun isSubscribed(tourism: Tourism) = tourism.isSubscribed
}