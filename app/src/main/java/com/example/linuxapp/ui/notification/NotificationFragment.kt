package com.example.linuxapp.ui.notification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.linuxapp.databinding.FragmentNotificationsBinding
import com.example.linuxapp.databinding.FragmentStatisticsBinding

class NotificationFragment : Fragment() {


    private lateinit var viewModel: NotificationViewModel
    private var _binding: FragmentNotificationsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(NotificationViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return _binding!!.root
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