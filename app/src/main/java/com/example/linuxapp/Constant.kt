package com.example.linuxapp

class Constant {
    enum class BroadcastConstants(val value: String) {
        PREFERENCES("com.example.time_reminder.shared_preferences"),
        FIRST_BUTTON("com.example.time_reminder.WIDGET_BUTTON_1_CLICKED"),
        SECOND_BUTTON("com.example.time_reminder.WIDGET_BUTTON_2_CLICKED"),
        THIRD_BUTTON("com.example.time_reminder.WIDGET_BUTTON_3_CLICKED"),
        FOURTH_BUTTON("com.example.time_reminder.WIDGET_BUTTON_4_CLICKED"),
        FIFTH_BUTTON("com.example.time_reminder.WIDGET_BUTTON_5_CLICKED"),

        ERROR("com.example.time_reminder.ERROR")
    }

    enum class TimeConstants(val value: String) {
        TimeStart("com.example.time_reminder.TIME_START_NOTIFICATION"),
        TimeEnd("com.example.time_reminder.TIME_END_NOTIFICATION"),
        TimeInterval("com.example.time_reminder.TIME_INTERVAL"),
        TimeLastUpdate("com.example.time_reminder.TIME_LAST_UPDATE"),
        NOTIFY_REQUEST("com.example.time_reminder.NOTIFY_REQUEST")
    }

    enum class NotificationConstants(val value: String) {
        PREFERENCES("com.example.time_reminder.PREFERENCES"),
        RECEIVE_NOTIFICATIONS ( "COM.EXAMPLE.TIME_NOTIFIER.RECEIVE_NOTIFICATIONS"),
        RECEIVE_AT_NIGHT("COM.EXAMPLE.TIME_NOTIFIER.RECEIVE_AT_NIGHT"),
        NIGHT_TIME_START ("COM.EXAMPLE.TIME_NOTIFIER.NIGHT_TIME_START"),
        NIGHT_TIME_END("COM.EXAMPLE.TIME_NOTIFIER.NIGHT_TIME_END"),
        NOTIFICATION_PERIOD("COM.EXAMPLE.TIME_NOTIFIER.NOTIFICATION_PERIOD"),
        NOTIFICATION_TEXT("COM.EXAMPLE.TIME_NOTIFIER.NOTIFICATION_TEXT")
    }
}