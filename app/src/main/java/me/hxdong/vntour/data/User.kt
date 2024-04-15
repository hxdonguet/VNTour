package me.hxdong.vntour.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val username: String,
    val password: String,
    val avatarUrl: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val city: String
) {
    companion object {
        fun default(): User {
            return User(
                username = "hxdong",
                password = "",
                avatarUrl = "",
                fullName = "Hồ Xuân Đông",
                email = "hoxuandong@gmail.com",
                phoneNumber = "0123456789",
                city = "Hà Nội"
            )
        }
    }
}
