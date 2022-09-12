package com.medical.backend.controller
import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import com.medical.backend.service.PatientService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@WebFluxTest(PatientController::class)
@AutoConfigureWebTestClient

class PatientControllerTest {

    // need to mock the service layer responses

    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var patientService: PatientService

    @Autowired
    lateinit var patientRepository: PatientRepository

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun patientService() = mockk<PatientService>()

        @Bean
        fun patientRepository() = mockk<PatientRepository>()
    }

    @Test

    fun `should return list  `() {
        val patient1 = Patient(
            "1", "Chaitali", "Vadhane", "chv", "9325059460", "chv@gmail.com", "female", "30/03/1997",
            "chv@00", "Pune"
        )


        val expectedResult = mapOf(
            "patientId" to "1",
            "patientFirstName" to "Chaitali",
            "patientLastname" to "Vadhane",
            "userName" to "chv",
            "mobileNumber" to "9325059460",
            "email" to "chv@gmail.com",
            "gender" to "female",
            "dob" to "30/03/1997",
            "password" to "chv@00",
            "address" to "Pune"
        )
        every {
            patientService.findAllPatients()
        } returns Flux.just(patient1)

        val response = client.get()
            .uri("/patients/list")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            patientService.findAllPatients()
        }
    }

    @Test
    fun `should return one patient `() {

        val patient1 = Patient(
            "1", "Chaitali", "Vadhane", "chv", "9325059460", "chv@gmail.com", "female", "30/03/1997",
            "chv@00", "Pune"
        )


        val expectedResult = mapOf(
            "patientId" to "1",
            "patientFirstName" to "Chaitali",
            "patientLastname" to "Vadhane",
            "userName" to "chv",
            "mobileNumber" to "9325059460",
            "email" to "chv@gmail.com",
            "gender" to "female",
            "dob" to "30/03/1997",
            "password" to "chv@00",
            "address" to "Pune"
        )
        every {
            patientService.findById("1")
        } returns Mono.just(patient1)

        val response = client.get()
            .uri("/patients/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful
            .returnResult<Any>()
            .responseBody

        response.blockFirst() shouldBe expectedResult

        verify(exactly = 1) {
            patientService.findById("1")
        }
    }

    @Test
    fun `should register patient add patient when create api is being called`() {

        val exepectedResponse = mapOf(
            "patientId" to "1",
            "patientFirstName" to "Chaitali",
            "patientLastname" to "Vadhane",
            "userName" to "chv",
            "mobileNumber" to "9325059460",
            "email" to "chv@gmail.com",
            "gender" to "female",
            "dob" to "30/03/1997",
            "password" to "chv@00",
            "address" to "Pune"
        )
        val patient1 = Patient(
            "1", "Chaitali", "Vadhane", "chv", "9325059460", "chv@gmail.com", "female", "30/03/1997",
            "chv@00", "Pune"
        )

        every {
            patientService.addPatient(patient1)
        } returns Mono.just(patient1)

        val response = client.post()
            .uri("/patients/add")
            .bodyValue(patient1)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody


        response.blockFirst() shouldBe exepectedResponse

        verify(exactly = 1) {
            patientService.addPatient(patient1)
        }
    }
//    @Test
//    fun `should be able to update the patient`() {
//        val returnResult = mapOf(
//            "patientId" to "1",
//            "patientFirstName" to "Chaitali",
//            "patientLastname" to "Vadhane",
//            "userName" to "chv",
//            "mobileNumber" to "9325059460",
//            "email" to "chv@gmail.com",
//            "gender" to "female",
//            "dob" to "30/03/1997",
//            "password" to "chv@00",
//            "address" to "Pune"
//        )
//        val patient1 = Patient(
//            "1", "Chaitali", "Vadhane", "chv", "9325059460", "chv@gmail.com", "female", "30/03/1997",
//            "chv@00", "Pune"
//        )
//        every {
//            patientService.updatePatientById("1",patient1)
//        } returns Mono.just(patient1)
//
//        val response = client.put()
//            .uri("update/1")
//            .bodyValue(patient1)
//            .exchange()
//            .expectStatus().is2xxSuccessful
//
//        response.blockFirst() shouldBe returnResult
//
//
//        verify(exactly = 1) {
//            patientService.updatePatientById("1",patient1)
//        }
//    }
@Test

fun `should able to update patient`(){

    val expectedResponse = mapOf("patientId" to "2",
        "patientFirstName" to "John",
        "patientLastName" to "Cena",
        "userName" to "jcena",
        "mobileNumber" to "7866557788",
        "email" to "jc@gmail.com",
        "gender" to "male",
        "dob" to "12/09/2000",
        "password" to "jc@1234",
        "address" to "NYC")

    val patient = Patient(patientId="2", patientFirstName="John", patientLastname="Cena", userName="jcena",
        mobileNumber="7866557788", email="jc@gmail.com", gender="male", dob="12/09/2000",
        password="jc@1234", address="NYC")


    every {
        patientService.updatePatientById("2",patient)
    }returns Mono.just(patient)


    val response= client.put()
        .uri("/update/2")
        .bodyValue(patient)
        .exchange()// invoking the end point
        .expectStatus().is2xxSuccessful

    verify(exactly = 1){
        patientService.updatePatientById("2",patient)
    }
}

//    @Test
//
//    fun `should delete patient `() {
//        val patient1 = Patient(
//            "1", "Chaitali", "Vadhane", "chv", "9325059460", "chv@gmail.com", "female", "30/03/1997",
//            "chv@00", "Pune"
//        )
//
//
//        val expectedResult = mapOf(
//            "patientId" to "1",
//            "patientFirstName" to "Chaitali",
//            "patientLastname" to "Vadhane",
//            "userName" to "chv",
//            "mobileNumber" to "9325059460",
//            "email" to "chv@gmail.com",
//            "gender" to "female",
//            "dob" to "30/03/1997",
//            "password" to "chv@00",
//            "address" to "Pune"
//        )
//        every {
//            patientService.deleteById("1")
//        } returns Mono.just(patient1)
//
//        val response = client.delete()
//            .uri("/patients/1")
//            .accept(MediaType.APPLICATION_JSON)
//            .exchange() //invoking the end point
//            .expectStatus().is2xxSuccessful
//            .returnResult<Any>()
//            .responseBody
//
//        response.blockFirst() shouldBe expectedResult
//
//        verify(exactly = 1) {
//            patientService.deleteById("1")
//        }
//    }



 }






