package com.example.enrollmentplanner.core.data.model

import com.example.enrollmentplanner.core.base.extension.emptyString
import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.Date

data class UserModel(
    var id: String = emptyString,
    var name: String = emptyString,
    var cpf: String = emptyString,
    var creationDate: Long = 0,
    var birthDate: String = emptyString,
    var uf: String = emptyString,
    var phone: String = emptyString
) : Serializable {

    fun toFirebaseUser() = FirebaseUserModel(
        id = id,
        name = name,
        cpf = cpf,
        creationDate = if (creationDate != 0L) Timestamp(Date(creationDate)) else null,
        birthDate = birthDate,
        uf = uf,
        phone = phone
    )
}