package com.example.practicenycschoolapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicenycschoolapp.databinding.HeaderItemBinding
import com.example.practicenycschoolapp.databinding.SchoolItemBinding
import com.example.practicenycschoolapp.model.SchoolsItem

class SchoolsAdapter(
    private val onSchoolClicked: SchoolClickListener,
    private val schoolsDataSet: MutableList<SchoolType> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateNewSchools(newSchools: List<SchoolsItem>) {
        schoolsDataSet.clear()
        var tempChar = '0'

        newSchools.sortedBy { it.schoolName }.forEach {
            it.schoolName?.let { name ->
                if(!name.startsWith(tempChar)) {
                    schoolsDataSet.add(SchoolType.Header(name.first().toString()))
                }
            }
            schoolsDataSet.add(SchoolType.School(it))
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                SchoolsViewHolder(
                    SchoolItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            1 -> {
                HeaderViewHolder(
                    HeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                HeaderViewHolder(
                    HeaderItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = schoolsDataSet[position]) {
            is SchoolType.School -> (holder as SchoolsViewHolder).bind(item.schoolsItem, onSchoolClicked)
            is SchoolType.Header -> (holder as HeaderViewHolder).bind(item.letter)
        }
    }

    override fun getItemCount(): Int = schoolsDataSet.size

    override fun getItemViewType(position: Int): Int {
        return when (schoolsDataSet[position]) {
            is SchoolType.School -> SCHOOL_TYPE
            is SchoolType.Header -> HEADER_TYPE
        }
    }

    class SchoolsViewHolder(
        private val binding: SchoolItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(school: SchoolsItem, onSchoolClickListener: SchoolClickListener) {
            binding.schoolName.text = school.schoolName
            binding.primaryAddressLine1.text = school.primaryAddressLine1
            binding.schoolEmail.text = school.schoolEmail
            binding.website.text = school.website

            itemView.setOnClickListener {
                onSchoolClickListener.onSchoolClicked(school)
            }
        }
    }

    class HeaderViewHolder(
        private val binding: HeaderItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(letter: String) {}
    }

    companion object {
        private const val HEADER_TYPE = 1
        private const val SCHOOL_TYPE = 0
    }

}

interface SchoolClickListener {
    fun onSchoolClicked(school: SchoolsItem)
}

