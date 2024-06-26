package me.hxdong.vntour.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hxdong.vntour.EventBus
import me.hxdong.vntour.VNtourEvent
import me.hxdong.vntour.data.Schedule
import me.hxdong.vntour.data.Tourism
import me.hxdong.vntour.repository.TourismRepository


class TourDetailsViewModel constructor(private val repository: TourismRepository) : ViewModel() {
    val favorite = mutableStateOf(false)
    val subscribed = mutableStateOf(false)

    private val _listSelectedSchedule = mutableStateListOf<Int>()
    val listSelectedSchedule: List<Int> get() = _listSelectedSchedule

    fun updateFavoriteTourism(id: Int): String {
        viewModelScope.launch {
            repository.updateFavoriteTourism(id)
            isFavoriteTourism(id)
            EventBus.produceEvent(VNtourEvent.FAVORITE_CHANGED)
        }

        return ""
    }

    fun updateScheduleTourism(schedule: Schedule) {
        if (listSelectedSchedule.contains(schedule.id)) {
            _listSelectedSchedule.remove(schedule.id)
        } else {
            _listSelectedSchedule.add(schedule.id)
        }
    }

    private fun isFavoriteTourism(id: Int) {
        viewModelScope.launch {
            val result = repository.findTourismById(id)
            favorite.value = result.isFavorite
        }
    }

    fun updateSubscribedState(id: Int) {
        subscribed.value = !subscribed.value
        viewModelScope.launch {
            val result = repository.findTourismById(id)
            result.isSubscribed = subscribed.value
            EventBus.produceEvent(VNtourEvent.SUBSCRIBED_CHANGED)
        }
    }

    suspend fun getDetailById(id: Int): Tourism {
        return withContext(Dispatchers.IO) {
            return@withContext repository.findTourismById(id)
        }
    }

}