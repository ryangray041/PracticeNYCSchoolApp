package com.example.practicenycschoolapp.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface SchoolsRepository {
    fun getSchools(): Flow<ResultState>
    fun getSATScores(dbn: String): Flow<ResultState>
}

class SchoolsRepositoryImpl @Inject constructor(
    private val service: SchoolsApiService
): SchoolsRepository {
    override fun getSchools(): Flow<ResultState> =
        flow {
            emit(ResultState.LOADING)
            try {
                val response = service.getAllNYCSchools()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResultState.SUCCESS(it))
                    } ?: throw Exception ("BODY RESPONSE IS NULL")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: Exception) {
                emit(ResultState.ERROR(e))
            }
        }

    override fun getSATScores(dbn: String): Flow<ResultState> =
        flow {
            emit(ResultState.LOADING)
            try {
                val response = service.getSATScores(dbn)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResultState.SUCCESS(it))
                    } ?: throw Exception ("BODY RESPONSE IS NULL")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (e: Exception) {
                emit(ResultState.ERROR(e))
            }
        }

}

