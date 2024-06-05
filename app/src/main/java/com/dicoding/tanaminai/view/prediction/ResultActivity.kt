package com.dicoding.tanaminai.view.prediction

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.tanaminai.R
import com.dicoding.tanaminai.data.dummy.DataDummy
import com.dicoding.tanaminai.databinding.ActivityResultBinding
import com.google.android.material.tabs.TabLayoutMediator

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val predictionResult = intent.getStringExtra(EXTRA_RESULT) ?: "Tidak ada hasil prediksi"
        val inputN = intent.getStringExtra(EXTRA_N) ?: "Tidak ada hasil prediksi"
        val inputP = intent.getStringExtra(EXTRA_P) ?: "Tidak ada hasil prediksi"
        val inputK = intent.getStringExtra(EXTRA_K) ?: "Tidak ada hasil prediksi"
        val inputHum = intent.getStringExtra(EXTRA_HUM) ?: "Tidak ada hasil prediksi"
        val inputpH = intent.getStringExtra(EXTRA_PH) ?: "Tidak ada hasil prediksi"
        val inputTemp = intent.getStringExtra(EXTRA_TEMP) ?: "Tidak ada hasil prediksi"

        binding.apply {
            tvNitrogenValue.text = inputN
            tvPhosphorusValue.text = inputP
            tvPotassiumValue.text = inputK
            tvHumidityValue.text = inputHum
            tvPHValue.text = inputpH
            tvTemperatureValue.text = inputTemp
            tvPlantResult.text = predictionResult
        }

        val plantData = predictionResult.let { DataDummy.getPlantData(it) }
        Glide.with(binding.root)
            .load(plantData.image)
            .into(binding.imgResultPrediction)

        val sectionPagerAdapter = SectionPagerAdapter(this, predictionResult)
        val viewPager = binding.viewPagerDetail
        viewPager.adapter = sectionPagerAdapter
        val tab = binding.tabResult
        TabLayoutMediator(tab, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    companion object {
        const val EXTRA_RESULT = "extra_prediction_result"
        const val EXTRA_N = "extra_n"
        const val EXTRA_P = "extra_p"
        const val EXTRA_K = "extra_k"
        const val EXTRA_HUM = "extra_hum"
        const val EXTRA_PH = "extra_pH"
        const val EXTRA_TEMP = "extra_temp"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.description_result,
           R.string.maintenance_result
        )
    }
}