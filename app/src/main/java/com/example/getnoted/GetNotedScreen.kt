package com.example.getnoted

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getnoted.ui.SignInPage
import com.example.getnoted.ui.SignUpPage
import com.example.getnoted.ui.WelcomeScreen
import com.example.getnoted.viewmodel.AuthViewModel

enum class GetNotedScreen {
    Welcome,
    SignUp,
    SignIn
}

@Composable
fun GetNotedScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel()
) {
    // When viewmodel changes are made update UI state
    val uiState by authViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = GetNotedScreen.Welcome.name,
    ) {
        composable(route = GetNotedScreen.Welcome.name) {
            WelcomeScreen(
                onSignUpClicked = {
                    authViewModel.resetFields()
                    navController.navigate(GetNotedScreen.SignUp.name)
                },
                onSignInClicked = {
                    authViewModel.resetFields()
                    navController.navigate(GetNotedScreen.SignIn.name)
                },
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.SignUp.name) {
            SignUpPage(
                uiState = uiState,
                onEmailChange = { authViewModel.emailChanged(it) },
                onPasswordChange = { authViewModel.passwordChanged(it) },
                onPasswordConfirmChange = { authViewModel.passwordConfirmChanged(it) },
                onBackClicked = { cancelAuth(navController) },
                onSignUpClicked = { authViewModel.signUp() },
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.SignIn.name){
            SignInPage(
                uiState = uiState,
                onEmailChange = {authViewModel.emailChanged(it)},
                onPasswordChange = {authViewModel.passwordChanged(it)},
                onBackClicked = {cancelAuth(navController)},
                onSignInClicked = { authViewModel.signIn()},
                modifier = modifier
            )
        }
    }
}

fun cancelAuth(
    navController: NavHostController
) {
    navController.popBackStack(route = GetNotedScreen.Welcome.name, inclusive = false)
}
