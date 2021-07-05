package com.example.linuxapp.view.diagramm

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.linuxapp.R
import com.example.linuxapp.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class StatisticsFragment : Fragment() , View.OnClickListener{

    lateinit var navController: NavController
    private var _binding: FragmentStatisticsBinding? = null
    private lateinit var viewModel: StatisticsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(StatisticsViewModel::class.java)

        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return _binding!!.root
    }


    lateinit var pieChart: PieChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        pieChart = view.findViewById(R.id.pie_chart)

        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f,10f,5f,5f)

        pieChart.dragDecelerationFrictionCoef = 0.95f

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        pieChart.transparentCircleRadius = 61f

        val list: List<PieEntry> = List(3) { index: Int -> PieEntry(index.toFloat()+2f, "Label $index") }

        val pieData = PieDataSet(list,"Numbers")

        pieData.sliceSpace = 3f
        pieData.selectionShift = 5f
        pieData.colors = ColorTemplate.JOYFUL_COLORS.toList()

        val data = PieData(pieData)
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.YELLOW)

        pieChart.data = data
    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}