package com.example.practicenycschoolapp.domain

import com.example.practicenycschoolapp.model.SchoolsItem

data class DomainSchool(
    val dbn: String,
    val schoolName: String,
    val address: String,
    val zipCode: String,
    val overview: String,
    val email: String,
    val website: String,
    val phoneNumber: String
)

fun List<SchoolsItem>.mapToDomainSchool(): List<DomainSchool> {
    return this.map { school ->
        DomainSchool(
            dbn = school.dbn,
            schoolName = school.schoolName,
            address = school.primaryAddressLine1,
            zipCode = school.zip,
            overview = school.overviewParagraph,
            email = school.schoolEmail,
            website = school.website,
            phoneNumber = school.phoneNumber
        )
    }
}
