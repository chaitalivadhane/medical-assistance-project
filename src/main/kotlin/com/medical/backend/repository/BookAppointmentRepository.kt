package com.medical.backend.repository

import com.medical.backend.model.BookAppointment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookAppointmentRepository : ReactiveMongoRepository<BookAppointment, String> {
}