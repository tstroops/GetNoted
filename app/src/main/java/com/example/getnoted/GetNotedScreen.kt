package com.example.getnoted

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.getnoted.ui.WelcomeScreen
import androidx.navigation.compose.composable
import com.example.getnoted.ui.SignInPage
import com.example.getnoted.ui.SignUpPage


enum class GetNotedScreen(){
    Welcome,
    SignUp,
    SignIn
}

@Composable
fun GetNotedScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = GetNotedScreen.Welcome.name,
    ) {
        composable(route = GetNotedScreen.Welcome.name) {
            WelcomeScreen(
                onSignUpClicked = {navController.navigate(GetNotedScreen.SignUp.name)},
                onSignInClicked = {navController.navigate(GetNotedScreen.SignIn.name)},
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.SignUp.name){
            SignUpPage(
                onSignUpClicked = {/*TODO*/},
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.SignIn.name){
            SignInPage(
                modifier = modifier
            )
        }
    }
}