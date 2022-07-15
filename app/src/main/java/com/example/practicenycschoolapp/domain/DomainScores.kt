package com.example.practicenycschoolapp.domain

import com.example.practicenycschoolapp.model.SATScoresItem

data class DomainScores(
    val satReading: String,
    val satMath: String,
    val satWriting: String
)

fun List<SATScoresItem>.mapToDomainScores(): List<DomainScores> {
    return this.map { scores ->
        DomainScores(
            satReading = scores.satCriticalReadingAvgScore?: "No reading score available",
            satMath = scores.satMathAvgScore ?: "No math score available",
            satWriting = scores.satWritingAvgScore ?: "No writing score available",
        )
    }
}
