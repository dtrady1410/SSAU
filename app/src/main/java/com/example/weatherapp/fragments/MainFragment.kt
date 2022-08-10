package com.example.weatherapp.fragments

import android.Manifest
import android.app.Instrumentation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.Adapters.ViewAdaptDataClass
import com.example.weatherapp.Adapters.VpAdapter
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.security.Permission

class MainFragment : Fragment() {
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private val fragmentList = listOf(
        BlankFragmentHours.newInstance(),
        BlankFragmentDays.newInstance())
    private val API_KEY = "70207009f3194c0eb9093307220308 "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        VpFun()

    }

    private fun VpFun() = with(binding){
        val tList = listOf(getString(R.string.hours), getString(R.string.days))
        val adapter = VpAdapter(activity as FragmentActivity, fragmentList)
        vpCont.adapter = adapter
        TabLayoutMediator(tabLayout, vpCont){
            tab, position -> tab.text = (tList[position])
        }.attach()
    }

    private fun get_weather(city: String){
        val url = "http://api.weatherapi.com/v1/current.json?key=" +
                API_KEY +
                " &q=" +
                city +
                "&aqi=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET,
            url,
            {
                result -> WeatherParser(result)
            },
            {
                error -> binding.tvTemperature.text = "$error"
            }
        )
        queue.add(request)
    }

    private fun WeatherParser(result: String){
        val main_object = JSONObject(result)
        val list = DaysWeatherParser(main_object)
        CurrentWeatherParser(main_object, list[0])

    }

    private fun DaysWeatherParser(main_object: JSONObject): List<ViewAdaptDataClass> {
        val list = arrayListOf<ViewAdaptDataClass>()
        val Array_days = main_object.getJSONObject("forecast")
            .getJSONArray("forecastday")
        val city = main_object.getJSONObject("location").getString("name")
        for (i in 0 until Array_days.length()) {
            val day = Array_days[i] as JSONObject
            val item = ViewAdaptDataClass(
                city,
                day.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("text"),
                "",
                day.getString("date"),
                day.getJSONObject("day")
                    .getJSONObject("condition")
                    .getString("icon"),
                day.getJSONArray("hour").toString(),
                day.getJSONObject("day").getString("mintemp_c"),
                day.getJSONObject("day").getString("maxtemp_c")
            )
            list.add(item)
        }
        return list
    }

    private fun CurrentWeatherParser(main_object: JSONObject, weather_item: ViewAdaptDataClass){
        val item = ViewAdaptDataClass(
            main_object.getJSONObject("location").getString("name"),

            main_object.getJSONObject("current")
                .getJSONObject("condition")
                .getString("text"),

            main_object.getJSONObject("current").getString("temp_c"),
            main_object.getJSONObject("location").getString("localtime"),
            main_object.getJSONObject("current")
                .getJSONObject("condition").getString("icon"),
            weather_item.hour,
            weather_item.minTemp,
            weather_item.maxTemp
        )
    }

    private fun permissionListener(){
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(){
        if(!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}