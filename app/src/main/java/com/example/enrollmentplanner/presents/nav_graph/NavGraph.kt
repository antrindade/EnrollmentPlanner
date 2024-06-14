package com.example.enrollmentplanner.presents.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.enrollmentplanner.features.form.screen.FormScreen
import com.example.enrollmentplanner.features.home.screen.HomeScreen
import com.example.enrollmentplanner.presents.util.NavGraphEnum

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavGraphEnum.NAV_HOME_SCREEN.value) {
        composable(NavGraphEnum.NAV_HOME_SCREEN.value) { HomeScreen(navController)}
        composable(NavGraphEnum.NAV_FORM_SCREEN.value) { FormScreen(navController) }
    }
}