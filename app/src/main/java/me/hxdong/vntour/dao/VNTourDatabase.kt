package me.hxdong.vntour.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import me.hxdong.vntour.data.User

@Database(entities = [User::class], version = 3)
abstract class VNTourDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

