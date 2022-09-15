package com.medical.backend.repository

import com.medical.backend.model.HospitalList
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HospitalListRepository : ReactiveMongoRepository<HospitalList, String>