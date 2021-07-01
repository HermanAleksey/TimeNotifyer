package com.example.linuxapp.ui.notification

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationViewModel (sharedPreferences: SharedPreferences): ViewModel() {

    private val receiveNotifications: MutableLiveData<Boolean> = MutableLiveData(
        sharedPreferences.getBoolean(ConstantNotifications().RECEIVE_NOTIFICATIONS, false)
    )
    fun isReceiveNotifications(): LiveData<Boolean> {
        return receiveNotifications
    }

    private val receiveAtNight: MutableLiveData<Boolean> =MutableLiveData(
        sharedPreferences.getBoolean(ConstantNotifications().RECEIVE_AT_NIGHT, false)
    )
    fun isReceiveAtNight(): LiveData<Boolean> {
        return receiveAtNight
    }

    private val nightTimeStart: MutableLiveData<Int> = MutableLiveData(
        sharedPreferences.getInt(ConstantNotifications().NIGHT_TIME_START, 0)
    )
    fun getNightTimeStart(): LiveData<Int> {
        return nightTimeStart
    }
    fun setNightTimeStart(int: Int) {
        nightTimeStart.value = int
    }

    private val nightTimeEnd: MutableLiveData<Int> = MutableLiveData(
        sharedPreferences.getInt(ConstantNotifications().NIGHT_TIME_END, 0)
    )
    fun getNightTimeEnd(): LiveData<Int> {
        return nightTimeEnd
    }

    private val notificationPeriod: MutableLiveData<Int> = MutableLiveData(
        sharedPreferences.getInt(ConstantNotifications().NOTIFICATION_PERIOD, 0)
    )
    fun getNotificationPeriod(): LiveData<Int> {
        return notificationPeriod
    }

    private val notificationText: MutableLiveData<String> = MutableLiveData(
        sharedPreferences.getString(ConstantNotifications().NOTIFICATION_TEXT, "default value")
    )
    fun getNotificationText(): LiveData<String> {
        return notificationText
    }

}