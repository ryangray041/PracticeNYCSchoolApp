package com.example.practicenycschoolapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practicenycschoolapp.R
import com.example.practicenycschoolapp.databinding.FragmentSchoolsBinding
import com.example.practicenycschoolapp.databinding.FragmentScoresBinding
import com.example.practicenycschoolapp.model.SATScoresItem
import com.example.practicenycschoolapp.model.SchoolsItem
import com.example.practicenycschoolapp.network.ResultState
import com.example.practicenycschoolapp.utils.SchoolData
import com.example.practicenycschoolapp.utils.ScoreData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment : BaseFragment() {
 private val scoreDataSet: MutableList<SATScoresItem> = mutableListOf()
    private val binding by lazy {
        FragmentScoresBinding.inflate(layoutInflater)
    }

    private lateinit var schoolData: SchoolData



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            schoolData = it.getSerializable(SCHOOL_DATA) as SchoolData
        }
    }

    private fun update(newScore: List<SATScoresItem>){
        scoreDataSet.clear()
        scoreDataSet.addAll(newScore)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.schoolName.text = schoolData.schoolName
        binding.phoneNumber.text = schoolData.phoneNumber
        binding.primaryAddressLine1.text = schoolData.address
        binding.zip.text = schoolData.zipCode
        binding.schoolEmail.text = schoolData.email
        binding.website.text = schoolData.website
        binding.overview.text = schoolData.overview
        binding.satWriting.text = scoreDataSet[0].satWritingAvgScore
        binding.satReading.text = scoreDataSet[0].satCriticalReadingAvgScore
        binding.satMath.text = scoreDataSet[0].satMathAvgScore

        schoolViewModel.getDbn(schoolData.dbn)
        schoolViewModel.getSATScores()

        schoolViewModel.scores.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultState.LOADING -> {
                    Log.d("HERE", "LOADING")
                }
                is ResultState.SUCCESS<*> -> {
                    Log.d("HERE", "onCreateView: ${state.response}")
                    (state as ResultState.SUCCESS<SATScoresItem>).response
                    update(state.response)

                }
                is ResultState.ERROR -> {
                    Log.d("HERE", "${state.e}")
                }
            }
        }



        return binding.root
    }

    companion object {
        const val SCHOOL_DATA = "SCHOOL_DATA"
        const val SCORE_DATA = "SCORE_DATA"
    }
}