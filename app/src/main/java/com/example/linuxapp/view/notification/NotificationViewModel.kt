package com.example.linuxapp.view.notification

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.linuxapp.Constant.NotificationConstants

class NotificationViewModel(sharedPreferences: SharedPreferences) : ViewModel() {

    private val receiveNotifications: MutableLiveData<Boolean> = MutableLiveData(
        sharedPreferences.getBoolean(NotificationConstants.RECEIVE_NOTIFICATIONS.value, false)
    )

    fun isReceiveNotifications(): LiveData<Boolean> {
        return receiveNotifications
    }

    private val receiveAtNight: MutableLiveData<Boolean> = MutableLiveData(
        sharedPreferences.getBoolean(NotificationConstants.RECEIVE_AT_NIGHT.value, false)
    )

    fun isReceiveAtNight(): LiveData<Boolean> {
        return receiveAtNight
    }

    private val nightTimeStart: MutableLiveData<Int> = MutableLiveData(
        sharedPreferences.getInt(NotificationConstants.NIGHT_TIME_START.value, 23*60*60*1000)
    )

    fun getNightTimeStart(): LiveData<Int> {
        return nightTimeStart
    }

    private val nightTimeEnd: MutableLiveData<Int> = MutableLiveData(
        sharedPreferences.getInt(NotificationConstants.NIGHT_TIME_END.value, 8*60*60*1000)
    )

    fun getNightTimeEnd(): LiveData<Int> {
        return nightTimeEnd
    }

    private val notificationPeriod: MutableLiveData<Int> = MutableLiveData(
        sharedPreferences.getInt(NotificationConstants.NOTIFICATION_PERIOD.value, 0)
    )

    fun getNotificationPeriod(): LiveData<Int> {
        return notificationPeriod
    }

    private val notificationText: MutableLiveData<String> = MutableLiveData(
        sharedPreferences.getString(NotificationConstants.NOTIFICATION_TEXT.value, "default value")
    )

    fun getNotificationText(): LiveData<String> {
        return notificationText
    }

    fun updateTimeVariables(string: String, int: Int) {
        when (string) {
            NotificationConstants.NIGHT_TIME_START.value -> nightTimeStart.value = int
            NotificationConstants.NIGHT_TIME_END.value -> nightTimeEnd.value = int
            NotificationConstants.NOTIFICATION_PERIOD.value -> notificationPeriod.value = int
        }
    }
}