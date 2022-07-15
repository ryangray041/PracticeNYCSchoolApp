package com.example.practicenycschoolapp.adapter

import com.example.practicenycschoolapp.model.SchoolsItem

sealed class SchoolType {
    data class School(val schoolsItem: SchoolsItem): SchoolType()
    data class Header(val letter: String): SchoolType()
}
