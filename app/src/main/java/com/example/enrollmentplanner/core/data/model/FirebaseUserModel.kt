package com.example.enrollmentplanner.core.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@IgnoreExtraProperties
data class FirebaseUserModel(
    @SerializedName("name")
    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String? = "",

    @SerializedName("cpf")
    @get:PropertyName("cpf")
    @set:PropertyName("cpf")
    var cpf: String? = "",

    @SerializedName("creation_date")
    @get:PropertyName("creation_date")
    @set:PropertyName("creation_date")
    var creationDate: Timestamp? = null,

    @SerializedName("birth_date")
    @get:PropertyName("birth_date")
    @set:PropertyName("birth_date")
    var birthDate: String? = "",

    @SerializedName("uf")
    @get:PropertyName("uf")
    @set:PropertyName("uf")
    var uf: String? = "",

    @SerializedName("phone")
    @get:PropertyName("phone")
    @set:PropertyName("phone")
    var phone: String? = ""
) : Serializable {

    fun toUserModel() = UserModel(
        name = name ?: "",
        cpf = cpf ?: "",
        creationDate = creationDate?.toDate()?.time ?: 0L,
        birthDate = birthDate ?: "",
        uf = uf ?: "",
        phone = phone ?: ""
    )
}