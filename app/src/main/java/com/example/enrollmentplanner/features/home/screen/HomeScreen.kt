package com.example.enrollmentplanner.features.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.enrollmentplanner.core.data.model.UserModel
import com.example.enrollmentplanner.core.state.DataState
import com.example.enrollmentplanner.features.home.util.HomeStateEnum
import com.example.enrollmentplanner.features.home.util.HomeStringEnum
import com.example.enrollmentplanner.features.home.viewmodel.HomeViewModel
import com.example.enrollmentplanner.presents.components.card.AddressCard
import com.example.enrollmentplanner.presents.util.NavGraphEnum

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val userUiState by viewModel.userUiState.collectAsState()

    HomeContent(userUiState, navController)
}

@Composable
fun HomeContent(
    userUiState: DataState<List<UserModel>>,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        floatingActionButton = { FloatingActionButtonAdd(navController) }
    ) { padding ->
        StateHome(userUiState, modifier.padding(padding))
    }
}

@Composable
fun StateHome(
    userUiState: DataState<List<UserModel>>,
    modifier: Modifier = Modifier,
) {
    when (userUiState) {
        is DataState.Loading -> LoadingView(modifier)
        is DataState.Success -> UserListView(userUiState.data, modifier)
        is DataState.Error -> ErrorView(modifier)
    }
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun UserListView(users: List<UserModel>, modifier: Modifier = Modifier) {
    if (users.isEmpty()) {
        Column(
            modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = HomeStateEnum.EMPTY_LIST.value, fontSize = 18.sp, textAlign = TextAlign.Center)
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(users) { user ->
                AddressCard(user)
            }
        }
    }
}

@Composable
fun ErrorView(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = HomeStateEnum.ERROR.value)
    }
}

@Composable
fun FloatingActionButtonAdd(navController: NavController, modifier: Modifier = Modifier) {
    ExtendedFloatingActionButton(
        onClick = {
            navController.navigate(NavGraphEnum.NAV_FORM_SCREEN.value)
        },
        icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) },
        text = { Text(text = HomeStringEnum.ADD.value) },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    )
}