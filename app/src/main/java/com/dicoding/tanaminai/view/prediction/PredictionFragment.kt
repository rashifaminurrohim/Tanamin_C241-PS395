package com.dicoding.tanaminai.view.prediction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dicoding.tanaminai.databinding.FragmentPredictionBinding
import com.dicoding.tanaminai.ml.PredictionHelper


class PredictionFragment : Fragment() {

    private var _binding: FragmentPredictionBinding? = null
    private val binding get() = _binding!!

    private lateinit var predictionHelper: PredictionHelper
    private lateinit var predictionResult: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPredictionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        predictionHelper = PredictionHelper(
            context = requireActivity(),
            onResult = { result ->
                predictionResult = result
            },
            onError = { errorMessage ->
                Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
        binding.btnStartPrediction.setOnClickListener {
            val inputN : String = "24"
            val inputP : String = "140"
            val inputK : String = "205"
            val inputHum : String = "83.6"
            val inputpH : String = "5.9"
            val inputTemp : String = "12.8"

            predictionHelper.predict(inputN, inputP, inputK, inputHum, inputpH, inputTemp)
            val intent = Intent(requireActivity(), ResultActivity::class.java).apply {
                putExtra(ResultActivity.EXTRA_RESULT, predictionResult)
                putExtra(ResultActivity.EXTRA_N, inputN)
                putExtra(ResultActivity.EXTRA_P, inputP)
                putExtra(ResultActivity.EXTRA_K, inputK)
                putExtra(ResultActivity.EXTRA_HUM, inputHum)
                putExtra(ResultActivity.EXTRA_PH, inputpH)
                putExtra(ResultActivity.EXTRA_TEMP, inputTemp)
            }
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        predictionHelper.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}