package me.hxdong.vntour.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hxdong.vntour.data.User
import me.hxdong.vntour.repository.AuthService

enum class RegisterState {
    Idle, Register, Success, Fail
}

class RegisterViewModel constructor(private val authService: AuthService) : ViewModel() {

    val state = mutableStateOf(RegisterState.Idle)
    fun register(user: User) {
        // validation logic
        viewModelScope.launch(Dispatchers.IO) {
            val register = authService.register(user)
            withContext(Dispatchers.Main) {
                if (register) {
                    state.value = RegisterState.Success
                } else {
                    state.value = RegisterState.Fail
                }
            }
        }
    }
}