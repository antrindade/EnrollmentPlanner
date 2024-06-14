package com.example.enrollmentplanner.presents.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enrollmentplanner.core.base.extension.emptyString
import com.example.enrollmentplanner.core.data.model.UserModel
import com.example.enrollmentplanner.presents.components.UserEnum
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressCard(
    user: UserModel,
    onDeleteClick: (UserModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(contentColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        content = {
            Column(
                modifier = modifier.fillMaxWidth().padding(16.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = user.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    IconButton(
                        onClick = {
                            showBottomSheet = true
                            scope.launch { sheetState.show() }
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Show bottom sheet",
                            tint = Color.Black,
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                }

                Spacer(modifier = modifier.height(8.dp))

                UserInfoText(label = UserEnum.REGISTERED.value, value = formatCreationDate(user.creationDate))
                UserInfoText(label = UserEnum.CPF.value, value = user.cpf)
                UserInfoText(label = UserEnum.UF.value, value = user.uf)
                UserInfoText(label = UserEnum.BIRTH_DATE.value, value = user.birthDate)
                UserInfoText(label = UserEnum.PHONE.value, value = user.phone)
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = UserEnum.DELETE_USER.value, fontSize = 14.sp)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Button(
                                onClick = {
                                    onDeleteClick(user)
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Sim")
                            }

                            Button(
                                onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet = false
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Não")
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun UserInfoText(label: String, value: String) {
    Column {
        Text(
            text = "$label: $value",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AddressCardPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AddressCard(
            UserModel(
                name = emptyString,
                cpf = emptyString,
                creationDate = 1718135227022,
                birthDate = emptyString,
                uf = emptyString,
                phone = emptyString,
            ),
            onDeleteClick = {}
        )
    }
}

fun formatCreationDate(timestampMillis: Long): String {
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.of("America/Sao_Paulo"))

    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm:ss"))
}