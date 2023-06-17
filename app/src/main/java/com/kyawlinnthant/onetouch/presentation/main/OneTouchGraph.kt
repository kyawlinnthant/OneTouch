package com.kyawlinnthant.onetouch.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kyawlinnthant.onetouch.navigation.NavigationInstructor
import com.kyawlinnthant.onetouch.presentation.feed.FeedScreen
import com.kyawlinnthant.onetouch.presentation.login.LoginScreen
import com.kyawlinnthant.onetouch.presentation.password.ForgotPasswordScreen
import com.kyawlinnthant.onetouch.presentation.profile.ProfileScreen
import com.kyawlinnthant.onetouch.presentation.register.RegisterScreen

@Composable
fun OneTouchGraph() {
    val vm: MainViewModel = hiltViewModel()
    val isLoggedIn = vm.isLoggedIn.collectAsState()
    val instructor = vm.instructor
    val navController = rememberNavController()

    NavigationInstructor(
        instructor = instructor,
        controller = navController
    )

    isLoggedIn.value?.let {
        val startDestination = if (it) Graph.Feature.route else Graph.Auth.route
        NavHost(
            navController = navController,
            startDestination = startDestination,
            route = Graph.Root.route
        ) {
            authGraph(navController)
            feedGraph(navController)
        }
    }
}

fun NavGraphBuilder.authGraph(
    controller: NavHostController
) {
    navigation(
        startDestination = Screen.Login.name,
        route = Graph.Auth.route
    ) {
        composable(route = Screen.Login.name) {
            LoginScreen()
        }
        composable(route = Screen.Register.name) {
            RegisterScreen()
        }
        composable(route = Screen.ForgotPassword.name) {
            ForgotPasswordScreen()
        }
    }
}

fun NavGraphBuilder.feedGraph(
    controller: NavHostController
) {
    navigation(
        startDestination = Screen.Profile.name,
        route = Graph.Feature.route
    ) {
        composable(route = Screen.Profile.name) {
            ProfileScreen()
        }
        composable(route = Screen.Feed.name) {
            FeedScreen()
        }
    }
}

sealed class Graph(val route: String) {
    object Root : Graph("root")
    object Auth : Graph("authentication")
    object Feature : Graph("feature")
}

sealed class Screen(val name: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("password")
    object Profile : Screen("profile")
    object Feed : Screen("feed")
}
