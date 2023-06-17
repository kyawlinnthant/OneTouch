package com.kyawlinnthant.onetouch.navigation

import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

class AppNavigatorImpl @Inject constructor() : AppNavigator {
    override val instructor: Channel<NavigationIntent> = Channel(
        capacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    override fun back(route: String?, inclusive: Boolean) {
        instructor.trySend(
            NavigationIntent.Back(
                route = route,
                inclusive = inclusive
            )
        )
    }

    override fun to(
        route: String,
        popupToRoute: String?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        instructor.trySend(
            NavigationIntent.To(
                route = route,
                popupToRoute = popupToRoute,
                inclusive = inclusive,
                isSingleTop = isSingleTop
            )
        )
    }
}
