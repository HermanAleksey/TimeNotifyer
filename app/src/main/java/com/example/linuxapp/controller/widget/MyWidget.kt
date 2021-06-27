package com.example.linuxapp.controller.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.linuxapp.Constant
import com.example.linuxapp.Constant.BroadcastConstants
import com.example.linuxapp.R
import com.example.linuxapp.ui.MainActivity
import java.util.*


/**
 * Implementation of App Widget functionality.
 */
class MyWidget : AppWidgetProvider() {

    private val ACTION_UPDATE_CLICK = "action.UPDATE_CLICK"

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (ACTION_UPDATE_CLICK == intent?.action) {
            // if the user clicked next
            Log.e("TAG", "onReceive: ACTION_UPDATE_CLICK")

            //update update time
            val pref =
                context!!.getSharedPreferences(
                    BroadcastConstants.Storage.value,
                    Context.MODE_PRIVATE
                )
            val calendar =
                Calendar.getInstance()//DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

            val time = " ${calendar.get(Calendar.DAY_OF_MONTH)} ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
            pref.edit().putString(Constant.TimeConstants.TimeLastUpdate.value, time).apply()


            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisAppWidgetComponentName = ComponentName(context.packageName, javaClass.name)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponentName)
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {

            val views = RemoteViews(context.packageName, R.layout.my_widget)
            //prefs
            val pref = context.getSharedPreferences(
                BroadcastConstants.Storage.value,
                Context.MODE_PRIVATE
            )
            val time = pref.getString(
                Constant.TimeConstants.TimeLastUpdate.value,
                "-"
            ) //"${hours}:${mins}"

//            clock
//            val locale: Locale = context.resources.configuration.locale
//            val sdf = SimpleDateFormat("HH:mm",locale);
//            val time = sdf.format(Date(System.currentTimeMillis()))
            views.setTextViewText(R.id.tv_last_update_time, time)

            //adding intent to main activity on label
            val intentToApp = Intent(context, MainActivity::class.java)
            val pendingIntentToApp = PendingIntent.getActivity(context, 0, intentToApp, 0)
            views.setOnClickPendingIntent(R.id.tv_widget_main_label, pendingIntentToApp)

            //buttons on widget
            val buttons = IntArray(5)
            buttons[0] = R.id.button_1
            buttons[1] = R.id.button_2
            buttons[2] = R.id.button_3
            buttons[3] = R.id.button_4
            buttons[4] = R.id.button_5


            //adding onClick listeners to buttons
            for (i in 0..4) {
                val intentBroadcast = Intent(context, WidgetBroadcastReceiver::class.java)
                intentBroadcast.action = when (i) {
                    0 -> BroadcastConstants.FirstButton.value
                    1 -> BroadcastConstants.SecondButton.value
                    2 -> BroadcastConstants.ThirdButton.value
                    3 -> BroadcastConstants.FourthButton.value
                    4 -> BroadcastConstants.FifthButton.value
                    else -> BroadcastConstants.ERROR.value
                }
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intentBroadcast, 0)
                views.setOnClickPendingIntent(buttons[i], pendingIntent)

                //starting service to update widget
//                val serviceIntent = Intent(context,WidgetService::class.java)
//                val servicePendingIntent = PendingIntent.getService(context,0,serviceIntent,0)
//                serviceIntent.putExtra("IntArray",appWidgetIds)
//                views.setOnClickPendingIntent(buttons[i], servicePendingIntent)

                //self-update call
                views.setOnClickPendingIntent(
                    buttons[i],
                    getPendingSelfIntent(context, ACTION_UPDATE_CLICK)
                );

                //customization
                views.setImageViewResource(buttons[i], R.drawable.notification_icon)
//                views.setImageViewIcon(buttons[i], Icon.createWithResource(context,R.drawable.notification_icon))
            }

            /***/

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent? {
        val intent =
            Intent(context, javaClass) // An intent directed at the current class (the "self").
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}