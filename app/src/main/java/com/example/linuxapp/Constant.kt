package com.example.linuxapp

class Constant {
    enum class BroadcastConstants(val value: String) {
        Storage("com.example.time_reminder.shared_preferences"),
        FirstButton("com.example.time_reminder.WIDGET_BUTTON_1_CLICKED"),
        SecondButton("com.example.time_reminder.WIDGET_BUTTON_2_CLICKED"),
        ThirdButton("com.example.time_reminder.WIDGET_BUTTON_3_CLICKED"),
        FourthButton("com.example.time_reminder.WIDGET_BUTTON_4_CLICKED"),
        FifthButton("com.example.time_reminder.WIDGET_BUTTON_5_CLICKED"),

        ERROR("com.example.time_reminder.ERROR")
    }

    enum class TimeConstants(val value: String) {
        TimeStart("com.example.time_reminder.TIME_START_NOTIFICATION"),
        TimeEnd("com.example.time_reminder.TIME_END_NOTIFICATION"),
        TimeInterval("com.example.time_reminder.TIME_INTERVAL"),
        TimeLastUpdate("com.example.time_reminder.TIME_LAST_UPDATE"),
        NOTIFY_REQUEST("com.example.time_reminder.NOTIFY_REQUEST")
    }
}