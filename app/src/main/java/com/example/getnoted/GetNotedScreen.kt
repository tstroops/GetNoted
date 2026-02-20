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
import com.example.getnoted.ui.NotebooksPage
import com.example.getnoted.ui.NotesPage
import com.example.getnoted.ui.SignInPage
import com.example.getnoted.ui.SignUpPage
import com.example.getnoted.ui.WelcomeScreen
import com.example.getnoted.viewModel.AuthState
import com.example.getnoted.viewModel.AuthViewModel

enum class GetNotedScreen {
    Welcome,
    SignUp,
    SignIn,
    Notebooks,
    Notes
}

@Composable
fun GetNotedScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel()
) {
    // When viewmodel changes are made update UI state
    val uiState by authViewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (uiState.authState == AuthState.IsAuthorized) GetNotedScreen.Notebooks.name
            else GetNotedScreen.Welcome.name,
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
                onSignInClicked = { authViewModel.signIn(); if(uiState.authState == AuthState.IsAuthorized) navController.navigate(GetNotedScreen.Notebooks.name)},
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.Notebooks.name){
            NotebooksPage(
                onNotebookClicked = { navController.navigate(GetNotedScreen.Notes.name) },
                notebooks = arrayOf(1,2)
            )
        }

        composable(route = GetNotedScreen.Notes.name){
            NotesPage(
                onNoteClicked = {},
                notes = arrayOf(1,2)
            )
        }
    }
}

fun cancelAuth(
    navController: NavHostController
) {
    navController.popBackStack(route = GetNotedScreen.Welcome.name, inclusive = false)
}
