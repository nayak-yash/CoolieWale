package com.cooliewale.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cooliewale.login.ui.screens.LoginScreen
import com.cooliewale.login.ui.screens.MainScreen
import com.cooliewale.login.ui.screens.OtpScreen
import com.cooliewale.login.ui.theme.CoolieWaleTheme
import com.cooliewale.login.utils.Screens.LOGIN_SCREEN
import com.cooliewale.login.utils.Screens.MAIN_SCREEN
import com.cooliewale.login.utils.Screens.OTP_SCREEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoolieWaleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val temp = it
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN
    ) {
        composable(
            route = LOGIN_SCREEN,
            arguments = listOf(
                navArgument("number") {
                    type = NavType.StringType
                }
            )) {
            LoginScreen {
                navController.navigate("$OTP_SCREEN/${it}")
            }
        }
        composable(
            route = "$OTP_SCREEN/{number}",
            arguments = listOf(
                navArgument("number") {
                    type = NavType.StringType
                }
            )
        ) {
            OtpScreen {
                navController.navigate(MAIN_SCREEN)
            }
        }
        composable(route = MAIN_SCREEN) {
            MainScreen()
        }
    }
}
