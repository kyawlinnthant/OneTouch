package com.kyawlinnthant.onetouch.navigation

import kotlinx.coroutines.channels.Channel

interface AppNavigator {

    val instructor: Channel<NavigationIntent>

    fun back(
        route: String? = null,
        inclusive: Boolean = false
    )

    fun to(
        route: String,
        popupToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false
    )

}