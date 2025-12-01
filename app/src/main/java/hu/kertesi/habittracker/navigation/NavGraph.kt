package hu.kertesi.habittracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.kertesi.habittracker.data.AppReferences
import hu.kertesi.habittracker.ui.screens.EditScreen
import hu.kertesi.habittracker.ui.screens.HomeScreen
import hu.kertesi.habittracker.ui.screens.LoginScreen
import hu.kertesi.habittracker.ui.screens.SettingsScreen

@Composable
fun NavGraph(modifier: Modifier, fontScale: Float) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val (savedEmail, remembered) = AppReferences.loadLogin(context)
    val loggedIn = savedEmail.isNotEmpty() && remembered
    val startAt = if (loggedIn) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = startAt
    ) {
        composable ("login") {
            LoginScreen(
                modifier = modifier,
                fontScale = fontScale,
                navController = navController
            )
        }
        composable("home") {
            HomeScreen(
                modifier = modifier,
                fontScale = fontScale,
                navController = navController
            )
        }
        composable("edit") {
            EditScreen(
                modifier = modifier,
                fontScale = fontScale,
                navController = navController
            )
        }
        composable ("settings") {
            SettingsScreen(
                navController = navController
            )
        }
    }
}