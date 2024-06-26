package me.hxdong.vntour.repository

import me.hxdong.vntour.data.Tourism

interface TourismRepository {

    suspend fun findRecommendations(): List<Tourism>

    suspend fun findPopulars(): List<Tourism>

    suspend fun findTourByName(name: String): List<Tourism>

    suspend fun updateFavoriteTourism(id: Int)

    suspend fun findTourismById(id: Int): Tourism

    suspend fun findTourismByPredicate(predicate: (Tourism) -> Boolean): List<Tourism>

}