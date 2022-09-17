package com.medical.backend.service

import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk


import org.junit.jupiter.api.Test

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


class PatientServiceTest {
// mocking the repository layer response

    val patient1 = Patient(
        "1",
        "Chaitali",
        "Vadhane",
        "chv",
        "9325059460",
        "chv@gmail.com",
        "female",
        "30/03/1997",
        "chv@0",
        "Pune"
    )
    val patient2 =
        Patient("2", "Kunal", "Vadhane", "kuv", "9325059460", "kuv@gmail.com", "male", "18/05/2000", "kuv@0", "Pune")

    private val patientRepository = mockk<PatientRepository>() {

        every {
            findAll()
        } returns Flux.just(patient1, patient2)

        every {
            findById("1")
        } returns Mono.just(patient1)
    }
    private val patientService = PatientService(patientRepository)


    @Test
    fun `should return patients when find all method is called`() {

        val firstPatient = patientService.findAllPatients().blockFirst()
        val secondPatient = patientService.findAllPatients().blockLast()

        firstPatient shouldBe patient1
        secondPatient shouldBe patient2
    }

    @Test
    fun `should find the list of patients on the basis of the id`() {

        val result = patientService.findById("1").block()

        result shouldBe patient1
    }

}

