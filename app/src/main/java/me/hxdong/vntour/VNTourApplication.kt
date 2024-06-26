package me.hxdong.vntour

import android.app.Application
import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import me.hxdong.vntour.di.AppModule
import me.hxdong.vntour.di.AppModuleImpl

// At the top level of your kotlin file:
class VNTourApplication : Application() {

    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}

object EventBus {
    private val _events = MutableSharedFlow<VNtourEvent>() // private mutable shared flow
    val events = _events.asSharedFlow() // publicly exposed as read-only shared flow

    suspend fun produceEvent(event: VNtourEvent) {

        Log.d("EventBus", event.name)

        _events.emit(event) // suspends until all subscribers receive it
    }
}

enum class VNtourEvent {
    FAVORITE_CHANGED,
    SUBSCRIBED_CHANGED,
    USER_LOGIN,
}
