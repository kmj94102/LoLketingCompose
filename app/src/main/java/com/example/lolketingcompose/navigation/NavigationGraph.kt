package com.example.lolketingcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.auth.model.JoinInfo
import com.example.auth.model.UserInfoType
import com.example.lolketingcompose.ui.event.EventScreen
import com.example.lolketingcompose.ui.event.roulette.RouletteScreen
import com.example.lolketingcompose.ui.home.HomeScreen
import com.example.lolketingcompose.ui.login.LoginScreen
import com.example.lolketingcompose.ui.login.address.AddressScreen
import com.example.lolketingcompose.ui.login.join.JoinScreen
import com.example.lolketingcompose.ui.mypage.MyPageScreen
import com.example.lolketingcompose.ui.mypage.history.PurchaseHistoryScreen
import com.example.lolketingcompose.ui.mypage.modify.MyInfoModifyScreen
import com.example.lolketingcompose.ui.shop.ShopScreen
import com.example.lolketingcompose.ui.shop.cart.CartScreen
import com.example.lolketingcompose.ui.shop.detail.ShopDetailScreen
import com.example.lolketingcompose.ui.shop.purchase.PurchaseScreen
import com.example.lolketingcompose.ui.ticket.TicketListScreen
import com.example.lolketingcompose.ui.ticket.guide.TicketGuideScreen
import com.example.lolketingcompose.ui.ticket.history.TicketReservationHistoryScreen
import com.example.lolketingcompose.ui.ticket.reservation.TicketReservationScreen
import com.example.lolketingcompose.util.Constants
import com.example.lolketingcompose.util.argumentEncode

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    val onBackClick: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.item.routeWithPostFix
    ) {
        homeScreens(onBackClick, navController)
        myPageScreens(onBackClick, navController)
        eventScreens(onBackClick, navController)
        ticketScreens(onBackClick, navController)
        shopScreens(onBackClick, navController)
    }
}

fun NavGraphBuilder.homeScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    composable(
        route = NavScreen.Home.item.routeWithPostFix
    ) {
        HomeScreen(
            goToScreen = { navController.navigate(it) },
            goToLogin = {
                navController.navigate(NavScreen.Login.item.routeWithPostFix)
            }
        )
    }
    composable(
        route = NavScreen.Login.item.routeWithPostFix
    ) {
        LoginScreen(
            goToJoin = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.Join.item.route,
                        UserInfoType.Email.type,
                        argumentEncode(
                            JoinInfo.create().copy(type = UserInfoType.Email.type)
                        )
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

fun NavGraphBuilder.myPageScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    composable(
        route = NavScreen.MyPage.item.routeWithPostFix
    ) {
        MyPageScreen(
            onBackClick = onBackClick,
            goToModify = {
                navController.navigate(NavScreen.MyPageModify.item.routeWithPostFix)
            },
            goToPurchaseHistory = {
                navController.navigate(NavScreen.PurchaseHistory.item.routeWithPostFix)
            }
        )
    }

    composable(
        route = NavScreen.MyPageModify.item.routeWithPostFix
    ) { entry ->
        val address = entry
            .savedStateHandle
            .get<String>(Constants.Address)

        MyInfoModifyScreen(
            address = address,
            onBackClick = onBackClick,
            goToAddress = { navController.navigate(NavScreen.Address.item.routeWithPostFix) }
        )
    }

    composable(
        route = NavScreen.PurchaseHistory.item.routeWithPostFix
    ) {
        PurchaseHistoryScreen(
            onBackClick = onBackClick,
            goToTicketReservationHistory = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.TicketHistory.item.route,
                        it
                    )
                )
            }
        )
    }
}

fun NavGraphBuilder.eventScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    composable(
        route = NavScreen.LolketingEvent.item.routeWithPostFix
    ) {
        EventScreen(
            onBackClick = onBackClick,
            goToMyPage = {
                navController.navigate(NavScreen.MyPage.item.routeWithPostFix)
            },
            goToRoulette = {
                navController.navigate(NavScreen.Roulette.item.routeWithPostFix)
            }
        )
    }

    composable(
        route = NavScreen.Roulette.item.routeWithPostFix,
    ) {
        RouletteScreen(onBackClick = onBackClick)
    }
}

fun NavGraphBuilder.ticketScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    composable(
        route = NavScreen.TicketList.item.routeWithPostFix
    ) {
        TicketListScreen(
            onBackClick = onBackClick,
            goToReservation = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.TicketReservation.item.route,
                        it.toString()
                    )
                )
            }
        )
    }

    composable(
        route = NavScreen.TicketReservation.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.GameId) { type = NavType.IntType }
        )
    ) {
        TicketReservationScreen(
            onBackClick = onBackClick,
            goToTicketInfo = {
                navController.popBackStack()
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.TicketHistory.item.route,
                        it
                    )
                )
            },
            goToGuide = {
                navController.navigate(NavScreen.TicketGuide.item.routeWithPostFix)
            }
        )
    }

    composable(
        route = NavScreen.TicketHistory.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.GameId) { type = NavType.StringType }
        )
    ) {
        TicketReservationHistoryScreen(
            onBackClick = onBackClick,
            goToGuide = {
                navController.navigate(NavScreen.TicketGuide.item.routeWithPostFix)
            }
        )
    }

    composable(
        route = NavScreen.TicketGuide.item.routeWithPostFix
    ) {
        TicketGuideScreen(onBackClick = onBackClick)
    }
}

fun NavGraphBuilder.shopScreens(
    onBackClick: () -> Unit,
    navController: NavHostController
) {
    composable(
        route = NavScreen.Shop.item.routeWithPostFix
    ) {
        ShopScreen(
            onBackClick = onBackClick,
            goToDetail = {
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.ShopDetail.item.route,
                        it.toString()
                    )
                )
            },
            goToCart = {
                navController.navigate(NavScreen.Cart.item.routeWithPostFix)
            }
        )
    }

    composable(
        route = NavScreen.ShopDetail.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.GoodsId) { type = NavType.IntType }
        )
    ) {
        val goodsId = it.arguments?.getInt(Constants.GoodsId, 0)

        ShopDetailScreen(
            onBackClick = onBackClick,
            goToCart = {
                navController.navigate(NavScreen.Cart.item.routeWithPostFix)
            },
            goToPurchase = { entity ->
                if (goodsId != null && goodsId != 0) {
                    navController.navigate(
                        makeRouteWithArgs(
                            NavScreen.ShopPurchase.item.route,
                            argumentEncode(listOf(entity))
                        )
                    )
                }
            }
        )
    }

    composable(
        route = NavScreen.ShopPurchase.item.routeWithPostFix,
        arguments = listOf(
            navArgument(Constants.PurchaseData) { type = NavType.StringType }
        )
    ) { entry ->
        val address = entry
            .savedStateHandle
            .get<String>(Constants.Address)

        PurchaseScreen(
            onBackClick = onBackClick,
            goToShop = {
                navController.navigate(NavScreen.Shop.item.routeWithPostFix) {
                    popUpTo(NavScreen.Home.item.routeWithPostFix) { inclusive = false }
                }
            },
            goToAddress = {
                navController.navigate(NavScreen.Address.item.routeWithPostFix)
            },
            newAddress = address
        )
    }

    composable(
        route = NavScreen.Cart.item.routeWithPostFix
    ) {
        CartScreen(
            onBackClick = onBackClick,
            goToPurchase = { list ->
                navController.navigate(
                    makeRouteWithArgs(
                        NavScreen.ShopPurchase.item.route,
                        argumentEncode(list)
                    )
                )
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