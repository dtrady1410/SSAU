package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.Adapters.ViewAdaptDataClass
import com.example.weatherapp.Adapters.weather_adapter
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentBlankHoursBinding
import com.example.weatherapp.databinding.FragmentMainBinding

class BlankFragmentHours : Fragment() {
    lateinit var binding: FragmentBlankHoursBinding
    private lateinit var adapter: weather_adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Rcview()
    }

    private fun Rcview() = with(binding){
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = weather_adapter()
        rcView.adapter = adapter
        val list  = listOf(ViewAdaptDataClass("Izhevsk", "Sunny", "25",
            "12:00", "", "", "", ""))
        adapter.submitList(list)

    }

    companion object {
        @JvmStatic
        fun newInstance() = BlankFragmentHours()
    }

}