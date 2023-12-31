package com.example.lolketingcompose.navigation

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
}