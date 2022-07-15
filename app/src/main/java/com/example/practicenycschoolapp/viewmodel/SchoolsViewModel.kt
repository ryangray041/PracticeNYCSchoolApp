package com.example.practicenycschoolapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practicenycschoolapp.network.ResultState
import com.example.practicenycschoolapp.network.SchoolsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    private val schoolsRepository: SchoolsRepository,
    private val ioDispatcher: CoroutineDispatcher
): BaseViewModel() {

    private val _schools: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val schools: LiveData<ResultState> get() = _schools

    private val _scores: MutableLiveData<ResultState> = MutableLiveData(ResultState.LOADING)
    val scores: LiveData<ResultState> get() = _scores

    private var schoolsJob: Job? = null

    lateinit var dbn: String

    fun getDbn(newDbn: String){
        dbn = newDbn
    }

    fun getSchools() {
        schoolsJob = safeViewModelScope.launch(ioDispatcher) {
            schoolsRepository.getSchools().collect {
                _schools.postValue(it)
            }
        }
    }

    fun getSATScores() {
        schoolsJob = safeViewModelScope.launch(ioDispatcher) {
            schoolsRepository.getSATScores(dbn).collect {
                _scores.postValue(it)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        schoolsJob?.cancel()
    }

}