package com.example.lolketingcompose.navigation

import com.example.lolketingcompose.util.Constants

sealed class NavScreen(val item: NavItem) {
    object Home: NavScreen(
        NavItem(
            route = "Home"
        )
    )

    object Login: NavScreen(
        NavItem(
            route = "Login"
        )
    )

    object Join: NavScreen(
        NavItem(
            route = "Join",
            routeWithPostFix = "Join/{type}/{data}"
        )
    ) {
        const val Type = "type"
        const val Data = "data"
    }

    object Address: NavScreen(
        NavItem(
            route = "Address"
        )
    )

    object MyPage: NavScreen(
        NavItem(
            route = "MyPage"
        )
    )

    object PurchaseHistory: NavScreen(
        NavItem(
            route = "PurchaseHistory"
        )
    )

    object MyPageModify: NavScreen(
        NavItem(
            route = "MyPageModify"
        )
    )

    object LolketingEvent: NavScreen(
        NavItem(
            route = "LolketingEvent"
        )
    )

    object Roulette: NavScreen(
        NavItem(
            route = "Roulette",
            routeWithPostFix = "Roulette/{${Constants.UserId}}"
        )
    )

    object TicketList: NavScreen(
        NavItem(
            route = "TicketList"
        )
    )

    object TicketReservation: NavScreen(
        NavItem(
            route = "TicketReservation",
            routeWithPostFix = "TicketReservation/{${Constants.GameId}}"
        )
    )

    object TicketHistory: NavScreen(
        NavItem(
            route = "TicketHistory",
            routeWithPostFix = "TicketHistory/{${Constants.GameId}}"
        )
    )

    object Shop: NavScreen(
        NavItem(
            route = "Shop"
        )
    )

    object ShopDetail: NavScreen(
        NavItem(
            route = "ShopDetail",
            routeWithPostFix = "ShopDetail/{${Constants.GoodsId}}"
        )
    )

    object ShopPurchase: NavScreen(
        NavItem(
            route = "ShopPurchase",
            routeWithPostFix = "ShopPurchase/{${Constants.PurchaseData}}"
        )
    )

    object Cart: NavScreen(
        NavItem(
            route = "Cart"
        )
    )
}