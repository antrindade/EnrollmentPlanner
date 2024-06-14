package com.example.enrollmentplanner.core.data.model

import com.example.enrollmentplanner.core.base.extension.emptyString
import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@IgnoreExtraProperties
data class FirebaseUserModel(
    @SerializedName("id")
    @get:PropertyName("id")
    @set:PropertyName("id")
    var id: String? = emptyString,

    @SerializedName("name")
    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String? = emptyString,

    @SerializedName("cpf")
    @get:PropertyName("cpf")
    @set:PropertyName("cpf")
    var cpf: String? = emptyString,

    @SerializedName("creation_date")
    @get:PropertyName("creation_date")
    @set:PropertyName("creation_date")
    var creationDate: Timestamp? = null,

    @SerializedName("birth_date")
    @get:PropertyName("birth_date")
    @set:PropertyName("birth_date")
    var birthDate: String? = emptyString,

    @SerializedName("uf")
    @get:PropertyName("uf")
    @set:PropertyName("uf")
    var uf: String? = emptyString,

    @SerializedName("phone")
    @get:PropertyName("phone")
    @set:PropertyName("phone")
    var phone: String? = emptyString
) : Serializable {

    fun toUserModel() = UserModel(
        id = id ?: emptyString,
        name = name ?: emptyString,
        cpf = cpf ?: emptyString,
        creationDate = creationDate?.toDate()?.time ?: 0L,
        birthDate = birthDate ?: emptyString,
        uf = uf ?: emptyString,
        phone = phone ?: emptyString
    )
}