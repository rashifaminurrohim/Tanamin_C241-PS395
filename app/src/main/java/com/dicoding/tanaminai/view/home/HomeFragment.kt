package com.dicoding.tanaminai.view.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.DecimalFormat
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.tanaminai.R
import com.dicoding.tanaminai.data.weather.WeatherResponse
import com.dicoding.tanaminai.databinding.FragmentHomeBinding
import com.dicoding.tanaminai.utils.ResultState
import com.dicoding.tanaminai.view.prediction.PredictionFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncherLocation =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyWeather()
                }

                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyWeather()
                }

                else -> {
                    // No location access granted.
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getMyWeather()
        binding.linearMain.setOnClickListener {
//            findNavController().navigate(R.id.act_homeFrag_to_predictionFrag)
        }


    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyWeather() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location ->
                if (location != null) {
                    currentLocation = location
                    val lat = currentLocation.latitude
                    val lon = currentLocation.longitude
                    homeViewModel.getCurrentWeather(lat, lon, apiKey = APPID)
                        .observe(viewLifecycleOwner) { resultState: ResultState<WeatherResponse> ->
                            when (resultState) {
                                is ResultState.Loading -> {
                                    showToast("Getting Weather")
                                }

                                is ResultState.Success -> {
                                    val weatherData = resultState.data
                                    val kalvin = weatherData.main?.temp
                                    val celcius = kalvin?.minus(273.15)
                                    val temperature = celcius?.toInt()
                                    val condition = weatherData.weather?.get(0)?.description
                                    val city = weatherData.name
                                    val country = weatherData.sys?.country
                                    iconWeather(condition)
                                    binding.apply {
                                        tvLocation.text = "$city, $country"
                                        tvWeatherTemp.text = "$temperature"
                                        tvWeatherCondition.text = condition
                                    }


                                }

                                is ResultState.Error -> {
                                    Log.e("CURRENT LOCATION", "getMyLocation: ${resultState.error}")
                                    showToast("Error")
                                }
                            }

                        }
                } else {
                    showToast("Location not found. Try Again")
                }
            }
        } else {
            requestPermissionLauncherLocation.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun iconWeather(condition: String?) {
        if (condition != null) {
            when {
                condition.contains("clear") -> {
                    binding.imgWeather.setImageResource(R.drawable.sun)
                }
                condition.contains("thunder") -> {
                    binding.imgWeather.setImageResource(R.drawable.thunder)
                }
                condition.contains("rain") -> {
                    binding.imgWeather.setImageResource(R.drawable.rain)
                }
                condition.contains("snow") -> {
                    binding.imgWeather.setImageResource(R.drawable.snow)
                }
                condition.contains("clouds") && condition.contains("overcast") -> {
                    binding.imgWeather.setImageResource(R.drawable.overcast)
                }
                condition.contains("clouds") -> {
                    binding.imgWeather.setImageResource(R.drawable.clouds)
                }
                else -> {
                    binding.imgWeather.setImageResource(R.drawable.def)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val APPID = "196abc4b6c8ee31eeb8c0eefd8330183"
    }
}