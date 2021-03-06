package com.example.linuxapp.view.notification

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.example.linuxapp.databinding.FragmentNotificationsBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.fragment.app.DialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.example.linuxapp.Constant.NotificationConstants
import com.example.linuxapp.controller.notif.NotificationBroadcastReceiver
import com.example.linuxapp.controller.notif.YourService
import it.sephiroth.android.library.numberpicker.doOnProgressChanged

class NotificationFragment : Fragment() {

    private lateinit var viewModel: NotificationViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var sharedPreferences: SharedPreferences

    class MainViewModelFactory(private val sharedPreferences: SharedPreferences) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NotificationViewModel(sharedPreferences) as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = context!!.getSharedPreferences(
            NotificationConstants.PREFERENCES.value,
            Context.MODE_PRIVATE
        )
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(sharedPreferences)
            ).get(NotificationViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        viewModel.isReceiveNotifications().observe(this, {
            _binding!!.switchReceiveNotifications.isChecked = it
        })

        viewModel.isReceiveAtNight().observe(this, {
            _binding!!.switchNotificationsAtNightTime.isChecked = it
        })

        viewModel.getNightTimeStart().observe(this, {
            val hours = it / 60
            val minutes = it % 60
            _binding!!.tvInfoStartNightTime.text = prettyTime(hours, minutes)
            _binding!!.tvSettingStartNightTime.text = prettyTime(hours, minutes)
        })

        viewModel.getNightTimeEnd().observe(this, {
            val hours = it / 60
            val minutes = it % 60
            _binding!!.tvSettingEndNightTime.text = prettyTime(hours, minutes)
            _binding!!.tvInfoEndNightTime.text = prettyTime(hours, minutes)
        })

        viewModel.getNotificationPeriod().observe(this, {
            _binding!!.numberPickerPeriod.progress = it / 60
            _binding!!.tvInfoNotificationPeriod.text = "${it / 60}"
        })

        viewModel.getNotificationText().observe(this, {
            _binding!!.tiNotificationText.setText(it.toString())
        })


        _binding!!.switchReceiveNotifications.setOnClickListener {
            it as SwitchMaterial
            sharedPreferences.edit()
                .putBoolean(
                    NotificationConstants.RECEIVE_NOTIFICATIONS.value,
                    it.isChecked
                ).apply()

            //when turning switch ON - setting alarm if turning OFF - canceling him
            if (it.isChecked) {
                activity!!.startService(Intent(context, YourService::class.java))
            } else {
                NotificationBroadcastReceiver().cancelAlarm(context!!)
            }
        }

        _binding!!.switchNotificationsAtNightTime.setOnClickListener {
            //TODO(???????? ???????????????????????? ?????????? ?????????????? "???????????????? ??????????" ?????????? ???????? ?????? ?????????????????? ??????????????????
            // ?????? ?????? ???????????????? - ???? ?????? ?????????? ???????????????? ?????????????????????? ?? ???????????? ??????????. ???? ???????????? 1 ??????.)
            it as SwitchMaterial
            sharedPreferences.edit()
                .putBoolean(
                    NotificationConstants.RECEIVE_AT_NIGHT.value,
                    it.isChecked
                ).apply()
        }
        _binding!!.btnApplyNotificationText.setOnClickListener {
            sharedPreferences.edit().putString(
                NotificationConstants.NOTIFICATION_TEXT.value,
                _binding!!.tiNotificationText.text.toString()
            ).apply()
        }
        _binding!!.ibSettingEndNightTime.setOnClickListener {
            showTimePickerDialog(NotificationConstants.NIGHT_TIME_END.value)
        }
        _binding!!.ibSettingStartNightTime.setOnClickListener {
            showTimePickerDialog(NotificationConstants.NIGHT_TIME_START.value)
        }
        _binding!!.numberPickerPeriod.doOnProgressChanged { _, progress, _ ->
            // progress changed
            sharedPreferences.edit().putInt(
                NotificationConstants.NOTIFICATION_PERIOD.value,
                progress
            ).apply()
            viewModel.setPeriod(progress)
        }

        return _binding!!.root
    }

    private fun showTimePickerDialog(string: String) {
        val isSystem24Hour = is24HourFormat(context)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
//TODO(???????????????? ?????? ???????????? ???????????? ?? ?????????????????? ???????????????? ??????)
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Appointment time")
                .build()

        picker.addOnPositiveButtonClickListener {
            sharedPreferences.edit().putInt(string, picker.hour * 60 + picker.minute).apply()
            viewModel.updateTimeVariables(string, picker.hour * 60 + picker.minute)
            resetNotificationAlarm()
        }

        picker.show(fragmentManager!!, "tag")
    }

    private fun prettyTime(hours: Int, minutes: Int): String {
        var str = "$hours:"
        str += if (minutes < 10) {
            "0$minutes"
        } else minutes.toString()
        return str
    }

    private fun resetNotificationAlarm() {
        NotificationBroadcastReceiver().cancelAlarm(context!!)
        activity!!.startService(Intent(context, YourService::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}