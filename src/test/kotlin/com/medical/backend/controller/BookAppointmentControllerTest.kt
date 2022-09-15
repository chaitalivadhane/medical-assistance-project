package com.medical.backend.controller

import com.medical.backend.model.BookAppointment
import com.medical.backend.model.Patient

import com.medical.backend.service.BookAppointmentService
import io.kotlintest.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import reactor.core.publisher.Mono

@WebFluxTest(BookAppointmentController::class)
@AutoConfigureWebTestClient

class BookAppointmentControllerTest {

    // need to mock the service layer responses

    @Autowired
    lateinit var client: WebTestClient

    @Autowired
    lateinit var bookAppointmentService: BookAppointmentService


    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun bookAppointmentService() = mockk<BookAppointmentService>()


    }


    @Test
    fun `should book appointment when create api is being called`() {

        val expectedResponse = mapOf(
            "patientId" to "1",
            "patientName" to "Chaitali",
            "doctorName" to "Dr.Shukala",
            "address" to "Pune",
            "email" to "chv@gmail.com",
            "mobileNumber" to "9325059460",
            "dateofAppointment" to "14/09/2022",
            "reason" to "Cold"

        )
        val bookAppointment1 = BookAppointment(
            "1", "Chaitali", "Dr.Shukala", "Pune", "chv@gmail.com", "9325059460",
            "14/09/2022","Cold"
        )

        every {
            bookAppointmentService.addAppointment(bookAppointment1)
        } returns Mono.just(bookAppointment1)

        val response = client.post()
            .uri("/bookAppointment")
            .bodyValue(bookAppointment1)
            .exchange()
            .expectStatus().is2xxSuccessful
            .returnResult<Any>().responseBody


        response.blockFirst() shouldBe expectedResponse

        verify(exactly = 1) {
            bookAppointmentService.addAppointment(bookAppointment1)
        }
    }

    @Test
    fun `should able to update appointment`(){

        val expectedResponse = mapOf(
            "patientId" to "1",
            "patientName" to "Chaitali",
            "doctorName" to "Dr.Shukala",
            "address" to "Pune",
            "email" to "chv@gmail.com",
            "mobileNumber" to "9325059460",
            "dateofAppointment" to "14/09/2022",
            "reason" to "Cold"

        )

        val bookAppointment = BookAppointment(
            "1", "Chaitali", "Dr.Shukala", "Pune", "chv@gmail.com", "9325059460",
            "14/09/2022","Cold"
        )
        every {
            bookAppointmentService.updateAppointmentById("1",bookAppointment)
        }returns Mono.just(bookAppointment)

        val response= client.put()
            .uri("/updateAppointment/1")
            .bodyValue(bookAppointment)
            .exchange()// invoking the end point
            .expectStatus().is2xxSuccessful

        verify(exactly = 1){
            bookAppointmentService.updateAppointmentById("1",bookAppointment)
        }
    }
    @Test
    fun `should delete patient `() {
        val bookAppointment = BookAppointment(
            "1", "Chaitali", "Dr.Shukala", "Pune", "chv@gmail.com", "9325059460",
            "14/09/2022","Cold"
        )


        val expectedResult = mapOf(
            "patientId" to "1",
            "patientName" to "Chaitali",
            "doctorName" to "Dr.Shukala",
            "address" to "Pune",
            "email" to "chv@gmail.com",
            "mobileNumber" to "9325059460",
            "dateofAppointment" to "14/09/2022",
            "reason" to "Cold"
        )
        every {
            bookAppointmentService.deleteById("1")
        } returns Mono.empty()

        val response = client.delete()
            .uri("/Appointment/1")
            // .accept(MediaType.APPLICATION_JSON)
            .exchange() //invoking the end point
            .expectStatus().is2xxSuccessful

        verify(exactly = 1) {
            bookAppointmentService.deleteById("1")
        }
    }

}


