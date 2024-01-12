package com.example.lolketingcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.auth.model.UserInfo
import com.example.lolketingcompose.ui.home.HomeScreen
import com.example.lolketingcompose.ui.login.LoginScreen
import com.example.lolketingcompose.ui.login.address.AddressScreen
import com.example.lolketingcompose.ui.login.join.JoinScreen
import com.example.lolketingcompose.util.Constants
import com.example.lolketingcompose.util.argumentEncode

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
        LoginScreen(
            goToJoin = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.Join.item.route,
                        "email",
                        argumentEncode(UserInfo.create())
                    )
                )
            },
            goToSocialJoin = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.Join.item.route,
                        it.type,
                        argumentEncode(it)
                    )
                )
            },
            goToHome = {
                navController.navigate(NavScreen.Home.item.routeWithPostFix) {
                    navController.popBackStack()
                }
            }
        )
    }

    composable(
        route = NavScreen.Join.item.routeWithPostFix,
        arguments = listOf(
            navArgument(NavScreen.Join.Type) { type = NavType.StringType },
            navArgument(NavScreen.Join.Data) { type = NavType.StringType },
        )
    ) { entry ->
        val address = entry
            .savedStateHandle
            .get<String>(Constants.Address)

        JoinScreen(
            address = address,
            onBack = onBackClick,
            goToAddress = {
                navController.navigate(NavScreen.Address.item.routeWithPostFix)
            },
            goToHome = {
                navController.navigate(NavScreen.Home.item.routeWithPostFix) {
                    navController.popBackStack()
                }
            }
        )
    }

    composable(
        route = NavScreen.Address.item.routeWithPostFix
    ) {
        AddressScreen(
            onBackClick = {
                onBackClick()
                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.Address, it)
            }
        )
    }
}

fun makeRouteWithArgs(route: String, vararg args: String): String = buildString {
    append(route)
    args.forEach {
        append("/$it")
    }
}