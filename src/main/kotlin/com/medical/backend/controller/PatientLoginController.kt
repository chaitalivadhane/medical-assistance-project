package com.medical.backend.controller
import com.medical.backend.model.LoginRequest
import com.medical.backend.model.Patient
import com.medical.backend.repository.PatientRepository
import com.medical.backend.service.PatientLoginService
import com.medical.backend.service.PatientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@CrossOrigin("*")

@RestController
//@RequestMapping("login")
class PatientLoginController(

    @Autowired
    val patientRepository: PatientRepository,
    val patientService: PatientService,
    val patientLoginService: PatientLoginService
) {

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest) : Mono<Patient>
    {
        return patientLoginService.patientLogin(loginRequest)
  }

    @PostMapping("/signup")
    fun authenticateUser(@RequestBody Patient: Patient?): ResponseEntity<*>? {
        //mail.sendMail(new NotificationEmail("Sign Up",registerRequest.getEmail(),"You have successfully created account with us!!!\n"+"Click here to login with your account- \n"+"https://localhost:8080/sign_in"));
        return ResponseEntity("User Registered Successfully!!!", HttpStatus.OK)
    }

}