package com.medical.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class HospitalList(

    @Id
    val hospitalId: String?,
    val hospitalName: String?,
    val hospitalPhNum: String?,
    val hospitalAddress :String?,
)