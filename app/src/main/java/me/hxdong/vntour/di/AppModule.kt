package me.hxdong.vntour.di

import android.content.Context
import androidx.room.Room
import me.hxdong.vntour.dao.VNTourDatabase
import me.hxdong.vntour.repository.AuthService
import me.hxdong.vntour.repository.RoomAuthService
import me.hxdong.vntour.repository.RoomTourismRepository
import me.hxdong.vntour.repository.TourismRepository

interface AppModule {
    val tourRepo: TourismRepository
    val authService: AuthService
}

class AppModuleImpl(appContext: Context) : AppModule {

    private val database = Room.databaseBuilder(
        appContext,
        VNTourDatabase::class.java, "vntour-db"
    ).build()

    override val tourRepo: TourismRepository by lazy {
        RoomTourismRepository(database.userDao())
    }

    override val authService: AuthService by lazy {
        RoomAuthService(database.userDao())
    }
}

