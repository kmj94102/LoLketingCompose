package com.example.lolketingcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lolketingcompose.ui.home.HomeScreen
import com.example.lolketingcompose.ui.home.login.LoginScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    val onBackClick: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = NavScreen.Login.item.routeWithPostFix
    ) {

        homeScreens(onBackClick, navController)
    }
}

fun NavGraphBuilder.homeScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    composable(
        route = NavScreen.Home.item.routeWithPostFix
    ) {
        HomeScreen()
    }
    composable(
        route = NavScreen.Login.item.routeWithPostFix
    ) {
        LoginScreen()
    }
}