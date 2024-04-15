package me.hxdong.vntour.repository

import kotlinx.coroutines.delay
import me.hxdong.vntour.dao.UserDao
import me.hxdong.vntour.data.ListOfTour
import me.hxdong.vntour.data.Tourism

class RoomTourismRepository constructor(val tourDao: UserDao) : TourismRepository {

    override suspend fun findRecommendations(): List<Tourism> {
        delay(200)
        return ListOfTour.dummyTourism
    }

    override suspend fun findPopulars(): List<Tourism> {
        delay(200)
        return ListOfTour.dummyTourism
    }

    override suspend fun findTourByName(name: String): List<Tourism> {
        delay(200)
        if (name.isBlank()) {
            return emptyList()
        }
        return ListOfTour.dummyTourism.filter { tour -> tour.name.contains(name) }
    }

    override suspend fun updateFavoriteTourism(id: Int) {
        delay(200)
        val tourism = findTourismById(id)
        tourism.isFavorite = !tourism.isFavorite
    }

    override suspend fun findTourismById(id: Int): Tourism {
        delay(100)
        return ListOfTour.dummyTourism.find { tourism -> tourism.id == id }
            ?: Tourism.default()
    }

    override suspend fun findTourismByPredicate(predicate: (Tourism) -> Boolean): List<Tourism> {
        return ListOfTour.dummyTourism.filter { predicate(it) }
    }

}