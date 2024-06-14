package com.example.enrollmentplanner.features.form.ultil

enum class FormStringTextFieldEnum(val label: String, val placeholder: String) {
    NAME("Digite seu nome", "Nome"),
    CPF("Digite seu CPF", "CPF"),
    BIRTH_DATE("Digite sua data de nascimento", "dd/MM/yyyy"),
    UF("Digite a UF do seu estado", "UF"),
    PHONE("Digite seu telefone", "(99) 99999-9999")

}

enum class FormAlertEnum(val value: String) {
    ERROR_STATE("Erro ao salvar dados"),
    MANDATORY_CPF("CPF Obrigatorio"),
    OVER_18("Deve ser maior de 18 anos")
}

enum class FormOtherEnum(val value: String) {
    SP("SP"),
    MG("MG"),
    BUTTON_TEXT("Cadastrar")
}