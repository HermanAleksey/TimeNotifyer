package com.example.linuxapp.ui.notification

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.linuxapp.databinding.FragmentNotificationsBinding
import com.example.linuxapp.databinding.FragmentStatisticsBinding
import com.example.linuxapp.ui.MainActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.MainScope
import androidx.fragment.app.DialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

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
            "Prefs",
            Context.MODE_PRIVATE
        )
        viewModel =
            ViewModelProvider(
                this,
                MainViewModelFactory(
                    sharedPreferences
                )
            )
                .get(NotificationViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        viewModel.isReceiveNotifications().observe(this, {
            _binding!!.switchReceiveNotifications.isChecked = it
        })

        viewModel.isReceiveAtNight().observe(this, {
            _binding!!.switchNotificationsAtNightTime.isChecked = it
        })

        viewModel.getNightTimeStart().observe(this, {
            _binding!!.tvInfoStartNightTime.text = it.toString()
            _binding!!.tvSettingStartNightTime.text = it.toString()
        })

        viewModel.getNightTimeEnd().observe(this, {
            _binding!!.tvSettingEndNightTime.text = it.toString()
            _binding!!.tvInfoEndNightTime.text = it.toString()
        })

        viewModel.getNotificationPeriod().observe(this, {
            _binding!!.tvNotificationPeriod.text = it.toString()
            _binding!!.tvInfoNotificationPeriod.text = it.toString()
        })

        viewModel.getNotificationText().observe(this, {
            _binding!!.tiNotificationText.setText(it.toString())
        })


        _binding!!.switchReceiveNotifications.setOnClickListener {
            it as SwitchMaterial
            sharedPreferences.edit()
                .putBoolean(
                    ConstantNotifications().RECEIVE_NOTIFICATIONS,
                    it.isChecked
                ).apply()
        }
        _binding!!.switchNotificationsAtNightTime.setOnClickListener {
            it as SwitchMaterial
            sharedPreferences.edit()
                .putBoolean(
                    ConstantNotifications().RECEIVE_AT_NIGHT,
                    it.isChecked
                ).apply()
        }
        _binding!!.btnApplyNotificationText.setOnClickListener {
            sharedPreferences.edit().putString(
                ConstantNotifications().NOTIFICATION_TEXT,
                _binding!!.tiNotificationText.text.toString()
            ).apply()
        }
        _binding!!.ibSettingEndNightTime.setOnClickListener {
            showTimePickerDialog(ConstantNotifications().NIGHT_TIME_END)
        }
        _binding!!.ibSettingPeriod.setOnClickListener {
            showTimePickerDialog(ConstantNotifications().NOTIFICATION_PERIOD)
        }
        _binding!!.ibSettingStartNightTime.setOnClickListener {
            showTimePickerDialog(ConstantNotifications().NIGHT_TIME_START)
        }

        return _binding!!.root
    }

    private fun showTimePickerDialog(string: String) {
        val isSystem24Hour = is24HourFormat(context)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Appointment time")
                .build()

        picker.addOnPositiveButtonClickListener {
            sharedPreferences.edit().putInt(string, picker.hour).apply()
            viewModel.setNightTimeStart(picker.hour)
        }

        picker.show(fragmentManager!!, "tag")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val multiSlider = _binding!!.multiSlider
//        multiSlider.step = 2
//        multiSlider.min = 1
//        multiSlider.max = 24

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}