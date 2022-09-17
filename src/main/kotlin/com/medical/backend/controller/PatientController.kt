package com.medical.backend.controller
import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import com.medical.backend.service.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@CrossOrigin("*")

@RestController
//@RequestMapping("v1")
class PatientController (

    @Autowired
   val patientRepository: PatientRepository,
   val patientService: PatientService
        ){

    @GetMapping("/patients/list")
    fun getAllPatients(): Flux<Patient> {
        return patientService.findAllPatients()
    }

    @GetMapping("/patients/{id}")
    fun getPatientById(@PathVariable id: String): Mono<Patient> {
        return patientService.findById(id)
    }

    @PostMapping("/patients/add")
    fun save(@RequestBody patient: Patient): Mono<Patient> {
        return patientService.addPatient(patient)
    }

//
@PutMapping("/update/{id}")
fun updateById( @PathVariable id: String,@RequestBody  patient: Patient): Mono<Patient> {
    return patientService.updatePatientById(id,patient)
}


    @DeleteMapping
    fun delete(): Mono<Void> {
        return patientRepository.deleteAll()
    }


    @DeleteMapping("/patients/{id}")
    fun deletePatient(@PathVariable id: String): Mono<Void> {
        return patientService.deleteById(id)
    }
}


