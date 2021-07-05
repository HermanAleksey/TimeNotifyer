package com.example.linuxapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel :ViewModel(){
    private val receiveNotifications: MutableLiveData<Boolean> = MutableLiveData(false)
    fun isReceiveNotifications(): LiveData<Boolean> {
        return receiveNotifications
    }

    private val receiveAtNight: MutableLiveData<Boolean> = MutableLiveData(false)
    fun isReceiveAtNight(): LiveData<Boolean> {
        return receiveAtNight
    }

    private val nightTimeStart: MutableLiveData<Int> = MutableLiveData((1000*60*60*   8))
    fun getNightTimeStart(): LiveData<Int> {
        return nightTimeStart
    }

    private val nightTimeEnd: MutableLiveData<Int> = MutableLiveData((1000*60*60*   24))
    fun getNightTimeEnd(): LiveData<Int> {
        return nightTimeEnd
    }

    private val notificationPeriod: MutableLiveData<Int> = MutableLiveData((1000*60*60*   1))
    fun getNotificationPeriod(): LiveData<Int> {
        return notificationPeriod
    }

    private val notificationText: MutableLiveData<String> = MutableLiveData("Time has come!")
    fun getNotificationText(): LiveData<String> {
        return notificationText
    }
}