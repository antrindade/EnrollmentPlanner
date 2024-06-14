package com.example.enrollmentplanner.presents.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MenuOptions(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color(0xFFF4EBFF))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Spacer(
            modifier = modifier
                .size(5.dp)
                .clip(shape = CircleShape)
                .background(color = Color(0xFFC595FF))
        )

        Spacer(
            modifier = modifier
                .size(5.dp)
                .clip(shape = CircleShape)
                .background(color = Color(0xFFC595FF))
        )

        Spacer(
            modifier = modifier
                .size(5.dp)
                .clip(shape = CircleShape)
                .background(color = Color(0xFFC595FF))
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun MenuOptionsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuOptions()
    }
}