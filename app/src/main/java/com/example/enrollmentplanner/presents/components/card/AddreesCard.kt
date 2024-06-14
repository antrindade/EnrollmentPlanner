package com.example.enrollmentplanner.presents.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.enrollmentplanner.core.base.extension.emptyString
import com.example.enrollmentplanner.core.data.model.UserModel
import com.example.enrollmentplanner.presents.components.menu.MenuOptions
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddressCard(user: UserModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        content = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = user.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    MenuOptions()
                }

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = "Cadastrado: ${formatCreationDate(user.creationDate)}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = "CPF: ${user.cpf}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = "UF: ${user.uf}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = "Nascimento: ${user.birthDate}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = modifier.height(8.dp))

                Text(
                    text = "Celular: ${user.phone}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

            }
        }
    )
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
                phone = emptyString
            )
        )
    }
}

fun formatCreationDate(timestampMillis: Long): String {
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMillis), ZoneId.of("America/Sao_Paulo"))

    return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm:ss"))
}