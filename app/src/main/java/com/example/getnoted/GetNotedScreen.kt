package com.example.getnoted

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.getnoted.ui.NotePage
import com.example.getnoted.ui.NotebooksPage
import com.example.getnoted.ui.NotesPage
import com.example.getnoted.ui.SignInPage
import com.example.getnoted.ui.SignUpPage
import com.example.getnoted.ui.WelcomeScreen
import com.example.getnoted.viewModel.AuthState
import com.example.getnoted.viewModel.AuthViewModel
import com.example.getnoted.viewModel.NotebooksViewModel
import com.example.getnoted.viewModel.NotesViewModel

enum class GetNotedScreen {
    Welcome,
    SignUp,
    SignIn,
    Notebooks,
    Notes,
    Note
}

@Composable
fun GetNotedScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = viewModel(),
    notebooksViewModel: NotebooksViewModel = viewModel(),
    notesViewModel: NotesViewModel = viewModel()
) {
    // When viewmodel changes are made update UI state
    val uiState by authViewModel.uiState.collectAsState()
    val nbUiState by notebooksViewModel.uiState.collectAsState()
    val notesUiState by notesViewModel.uiState.collectAsState()

    // Automatically detects authstate
    LaunchedEffect(uiState.authState) {
        when (uiState.authState) {
            AuthState.IsAuthorized -> {
                navController.navigate(GetNotedScreen.Notebooks.name) {
                    popUpTo(GetNotedScreen.Welcome.name) { inclusive = true }
                }
            }
            AuthState.NotAuthorized -> {
                navController.navigate(GetNotedScreen.Welcome.name) {
                    popUpTo(0) { inclusive = true } // Goes back to the start
                }
            }
            else -> {}
        }
    }

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
                onSignInClicked = { authViewModel.signIn() },
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.Notebooks.name){
            LaunchedEffect(Unit) {
                notebooksViewModel.getNotebooks()
            }
            NotebooksPage(
                uiState = nbUiState,
                onCreateNotebookClicked = {notebooksViewModel.toggleCreate()},
                onDeleteNotebookClicked = {notebooksViewModel.toggleDelete()},
                onSignOutClicked = { authViewModel.signOut() },
                onCancelNb = { notebooksViewModel.cancelRequest() },
                onNameChange = {notebooksViewModel.updateName(it)},
                onCreateConfirm = { notebooksViewModel.createNotebook() },
                onDeleteConfirm = { notebooksViewModel.deleteNotebook() },
                onNotebookClicked = { notebook ->
                    notebooksViewModel.selectNotebook(notebook)
                    notesViewModel.setNotebookId(notebook.id)
                    navController.navigate(GetNotedScreen.Notes.name)
                },
                notebooks = nbUiState.notebooks,
                modifier = modifier
            )
        }

        composable(route = GetNotedScreen.Notes.name){
            LaunchedEffect(Unit) {
                notesViewModel.getNotes()
            }
            NotesPage(
                onNoteClicked = { note ->                      // receives note
                    notesViewModel.selectNote(note)            // stores selected note
                    navController.navigate(GetNotedScreen.Note.name)
                },
                notes = notesUiState.notes,
                onCreateNoteClicked = { notesViewModel.toggleCreate() },
                onDeleteNoteClicked = { notesViewModel.toggleDelete() },
                onConfirmCreate = { notesViewModel.createNote() },
                onConfirmDelete = { notesViewModel.deleteNote()},
                onCancelNote =  { notesViewModel.cancelRequest() },
                onBackClicked = { navController.popBackStack(route = GetNotedScreen.Notebooks.name, inclusive = false) },
                onNameChange = { notesViewModel.updateName(it) },
                uiState = notesUiState
            )
        }

        composable(route = GetNotedScreen.Note.name) {
            NotePage(
                modifier = modifier,
                text = notesUiState.currentNoteText,
                onTextChanged = { notesViewModel.updateNoteText(it) },
                onBackClicked = { navController.popBackStack(route = GetNotedScreen.Notes.name, inclusive = false) },
                onSaveClicked = { notesViewModel.saveNote() }
            )
        }
    }
}

fun cancelAuth(
    navController: NavHostController
) {
    navController.popBackStack(route = GetNotedScreen.Welcome.name, inclusive = false)
}
