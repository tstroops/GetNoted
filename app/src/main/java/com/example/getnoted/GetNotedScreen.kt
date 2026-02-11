package com.example.getnoted

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getnoted.ui.SignInPage
import com.example.getnoted.ui.SignUpPage
import com.example.getnoted.ui.WelcomeScreen
import com.example.getnoted.viewModel.SignUpViewModel


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
            val signUpViewModel: SignUpViewModel = viewModel()
            SignUpPage(
                onBackClicked = {cancelAuth(navController)},
                onSignUpClicked = { signUpViewModel.signUp() },
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.SignIn.name){
            SignInPage(
                onBackClicked = {cancelAuth(navController)},
                onSignInClicked = {/*TODO*/},
                modifier = modifier
            )
        }
    }
}

fun cancelAuth(
    navController: NavHostController
){
    navController.popBackStack(route=GetNotedScreen.Welcome.name, inclusive = false)
}