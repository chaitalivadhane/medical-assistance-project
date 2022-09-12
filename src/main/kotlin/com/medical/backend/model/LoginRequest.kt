package com.medical.backend.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
class LoginRequest {
  var username:String = ""
  var password:String = ""
}
