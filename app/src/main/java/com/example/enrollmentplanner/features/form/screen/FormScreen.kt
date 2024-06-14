package com.example.enrollmentplanner.features.form.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.enrollmentplanner.R
import com.example.enrollmentplanner.core.base.extension.emptyString
import com.example.enrollmentplanner.core.data.model.UserModel
import com.example.enrollmentplanner.core.state.DataState
import com.example.enrollmentplanner.features.form.ultil.DateVisualTransformation
import com.example.enrollmentplanner.features.form.ultil.FormAlertEnum
import com.example.enrollmentplanner.features.form.ultil.FormOtherEnum
import com.example.enrollmentplanner.features.form.ultil.FormStringTextFieldEnum
import com.example.enrollmentplanner.features.form.ultil.PhoneVisualTransformation
import com.example.enrollmentplanner.features.form.ultil.cpfVisualTransformation
import com.example.enrollmentplanner.features.form.viewmodel.FormViewModel
import com.example.enrollmentplanner.presents.components.textfield.DefaultTextFieldWithPadding
import com.example.enrollmentplanner.presents.util.NavGraphEnum
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun FormScreen(navController: NavController) {
    val viewModel: FormViewModel = hiltViewModel()
    val userUiState by viewModel.setUserUi.collectAsState()

    StateForm(userUiState, navController)
    FormContent(viewModel = viewModel)
}

@Composable
fun StateForm(userUiState: DataState<Boolean>, navController: NavController) {
    when (userUiState) {
        is DataState.Loading -> LoadingState()
        is DataState.Success -> if (userUiState.data) NavigateToHome(navController)
        is DataState.Error -> ErrorState()
    }
}

@Composable
fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun NavigateToHome(navController: NavController) {
    LaunchedEffect(Unit) {
        navController.navigate(NavGraphEnum.NAV_HOME_SCREEN.value) {
            popUpTo(NavGraphEnum.NAV_FORM_SCREEN.value) { inclusive = true }
        }
    }
}

@Composable
fun ErrorState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = FormAlertEnum.ERROR_STATE.value, fontSize = 18.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun FormContent(viewModel: FormViewModel, modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf(emptyString) }
    var cpf by rememberSaveable { mutableStateOf(emptyString) }
    var birthDate by rememberSaveable { mutableStateOf(emptyString) }
    var stateCode by rememberSaveable { mutableStateOf(emptyString) }
    var phoneNumber by rememberSaveable { mutableStateOf(emptyString) }

    val visualTransformation = VisualTransformation

    val cpfFormat = remember(cpf, visualTransformation) {
        cpfVisualTransformation().filter(AnnotatedString(cpf))
    }.text.text
    val birthDateFormat = remember(birthDate, visualTransformation) {
        DateVisualTransformation().filter(AnnotatedString(birthDate))
    }.text.text
    val phoneNumberFormat = remember(phoneNumber, visualTransformation) {
        DateVisualTransformation().filter(AnnotatedString(phoneNumber))
    }.text.text

    var isCpfVisible by remember { mutableStateOf(true) }
    var isBirthDateVisible by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(painter = painterResource(R.drawable.user), contentDescription = null)
        }

        DefaultTextFieldWithPadding(
            value = name,
            label = FormStringTextFieldEnum.NAME.label,
            placeholder = FormStringTextFieldEnum.NAME.placeholder,
            keyboardType = KeyboardType.Text,
            onValueChange = { name = it }
        )

        DefaultTextFieldWithPadding(
            value = cpf,
            label = FormStringTextFieldEnum.CPF.label,
            placeholder = FormStringTextFieldEnum.CPF.placeholder,
            onValueChange = { newString ->
                if (newString.length <= 14) {
                    cpf = newString
                }
            },
            visualTransformation = cpfVisualTransformation(),
            charLimit = 11,
            keyboardType = KeyboardType.Number,
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            if (isCpfVisible) {
                Text(text = FormAlertEnum.MANDATORY_CPF.value, style = TextStyle(color = Color.Red))
            }
        }

        DefaultTextFieldWithPadding(
            value = birthDate,
            label = FormStringTextFieldEnum.BIRTH_DATE.label,
            placeholder = FormStringTextFieldEnum.BIRTH_DATE.placeholder,
            visualTransformation = DateVisualTransformation(),
            charLimit = 10,
            keyboardType = KeyboardType.Number,
            onValueChange = { newString ->
                if (newString.length <= 8) {
                    birthDate = newString
                }
            },
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            if (isBirthDateVisible) {
                Text(text = FormAlertEnum.OVER_18.value, style = TextStyle(color = Color.Red))
            }
        }

        DefaultTextFieldWithPadding(
            value = stateCode,
            label = FormStringTextFieldEnum.UF.label,
            placeholder = FormStringTextFieldEnum.UF.placeholder,
            charLimit = 2,
            keyboardType = KeyboardType.Text,
            onValueChange = { stateCode = it.uppercase() }
        )

        DefaultTextFieldWithPadding(
            value = phoneNumber,
            label = FormStringTextFieldEnum.PHONE.label,
            placeholder = FormStringTextFieldEnum.PHONE.placeholder,
            charLimit = 11,
            keyboardType = KeyboardType.Phone,
            visualTransformation = PhoneVisualTransformation(),
            onValueChange = { newString ->
                if (newString.length <= 14) {
                    phoneNumber = newString
                }
            },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val user = UserModel(
                        name = name,
                        cpf = cpfFormat,
                        birthDate = birthDateFormat,
                        uf = stateCode,
                        phone = phoneNumberFormat
                    )
                    if (isValid(user).first) {
                        viewModel.setUserData(user)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = FormOtherEnum.BUTTON_TEXT.value)
            }

            LaunchedEffect(stateCode) {
                val validationResultSP = isValid(UserModel(
                    name = name,
                    cpf = cpfFormat,
                    birthDate = birthDateFormat,
                    uf = stateCode,
                    phone = phoneNumberFormat))

                isCpfVisible = validationResultSP.first == false && validationResultSP.second == FormOtherEnum.SP.value
                isBirthDateVisible = validationResultSP.first == false && validationResultSP.second == FormOtherEnum.MG.value
            }
        }
    }
}

fun isValid(user: UserModel): Pair<Boolean, String?> {
    if (user.uf.isEmpty()) return Pair(false, emptyString)

    val isNameValid = user.name.isNotEmpty()
    val isCpfValid = user.cpf.isNotEmpty()
    val isBirthDateValid = user.birthDate.isNotEmpty()
    val isPhoneValid = user.phone.isNotEmpty()

    return when (user.uf) {
        FormOtherEnum.SP.value -> {
            if (isNameValid && isCpfValid && isBirthDateValid && isPhoneValid) {
                Pair(true, null)
            } else {
                if (!isCpfValid) Pair(false, FormOtherEnum.SP.value) else Pair(false, emptyString)
            }
        }
        FormOtherEnum.MG.value -> {
            if (isNameValid && isBirthDateValid && isPhoneValid && isOver18Years(user.birthDate)) {
                Pair(true, null)
            } else {
                if (!isOver18Years(user.birthDate)) Pair(false, FormOtherEnum.MG.value) else Pair(false, emptyString)
            }
        }
        else -> Pair(true, emptyString)
    }
}

fun isOver18Years(birthDateString: String): Boolean {
    if (birthDateString.isEmpty()) return false

    val birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    return ChronoUnit.YEARS.between(birthDate, LocalDate.now()) >= 18
}