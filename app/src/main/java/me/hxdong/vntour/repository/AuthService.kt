package me.hxdong.vntour.repository

import me.hxdong.vntour.data.User

interface AuthService {

    fun register(user: User): Boolean

    fun login(username: String, password: String): User

    fun currentUser(): User

    fun update(user: User)

    fun logout(): Boolean

}