package br.com.matheusxreis.dogallery.ui.providers

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

class Notifications {

    private var _notifications = mutableStateOf(0)

    fun getAmount() = _notifications.value;

    fun increment(){
        _notifications.value = _notifications.value+1
    }
    fun reset(){
        _notifications.value = 0;
    }

}

var NotificationLocalOf = compositionLocalOf<Notifications>{ error("Notifications context not found") }