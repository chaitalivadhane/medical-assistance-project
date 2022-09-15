package com.medical.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BookAppointment(

    @Id
    val patientId: String?,
    val patientName: String?,
    val doctorName:String?,
    val address :String?,
    val email:String?,
    val mobileNumber:String?,
        val dateofAppointment:String?,
//    val category:String?,
    val reason:String?,
    )