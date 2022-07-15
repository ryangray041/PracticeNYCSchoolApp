package com.example.practicenycschoolapp.network

import com.example.practicenycschoolapp.model.SchoolsItem

sealed class ResultState {
    object LOADING: ResultState()
    data class SUCCESS<T>(val response: List<T>): ResultState()
    data class ERROR(val e: Exception): ResultState()
}
