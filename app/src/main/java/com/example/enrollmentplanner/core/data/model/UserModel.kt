package com.example.enrollmentplanner.core.data.model

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.Date

data class UserModel(
    var name: String = "",
    var cpf: String = "",
    var creationDate: Long = 0,
    var birthDate: String = "",
    var uf: String = "",
    var phone: String = ""
) : Serializable {

    fun toFirebaseUser() = FirebaseUserModel(
        name = name,
        cpf = cpf,
        creationDate = if (creationDate != 0L) Timestamp(Date(creationDate)) else null,
        birthDate = birthDate,
        uf = uf,
        phone = phone
    )
}