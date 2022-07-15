package com.example.practicenycschoolapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicenycschoolapp.R
import com.example.practicenycschoolapp.adapter.SchoolClickListener
import com.example.practicenycschoolapp.adapter.SchoolsAdapter
import com.example.practicenycschoolapp.databinding.FragmentSchoolsBinding
import com.example.practicenycschoolapp.domain.DomainSchool
import com.example.practicenycschoolapp.model.SATScoresItem
import com.example.practicenycschoolapp.model.SchoolsItem
import com.example.practicenycschoolapp.network.ResultState
import com.example.practicenycschoolapp.utils.SchoolData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentSchoolsBinding.inflate(layoutInflater)
    }

    private val schoolsAdapter by lazy {
        SchoolsAdapter (
            object: SchoolClickListener {
                override fun onSchoolClicked(school: SchoolsItem) {
                    val schoolData = SchoolData(school.dbn, school.schoolName, school.primaryAddressLine1, school.zip, school.overviewParagraph,
                        school.schoolEmail, school.website, school.phoneNumber)
                        schoolViewModel.getDbn(schoolData.dbn)
                        schoolViewModel.getSATScores()
                    findNavController().navigate(
                        R.id.action_schoolsFragment_to_scoresFragment, bundleOf(
                            Pair(ScoresFragment.SCHOOL_DATA, schoolData)
                        )
                    )
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.schoolRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = schoolsAdapter
        }

        schoolViewModel.schools.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ResultState.LOADING -> {
                    Log.d("HERE", "LOADING")
                }
                is ResultState.SUCCESS<*> -> {
                    Log.d("HERE", "onCreateView: ${state.response}")
                    (state as ResultState.SUCCESS<SchoolsItem>).response
                    schoolsAdapter.updateNewSchools(state.response)
                }
                is ResultState.ERROR -> {
                    Log.d("HERE", "${state.e.toString()}")
                }
            }
        }

        schoolViewModel.getSchools()

        return binding.root
    }

}