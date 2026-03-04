package com.example.getnoted.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.GetNotedTheme
import com.example.getnoted.viewModel.Notebook
import com.example.getnoted.viewModel.NotebooksUiState

@Composable
fun NotebooksPage(
    uiState: NotebooksUiState,
    onNotebookClicked: () -> Unit, // we will use UI state, as that will hold the list of notebooks, just a placeholder for UI testing
    onCreateNotebookClicked: () -> Unit,
    onDeleteNotebookClicked: () -> Unit,
    onCancelNb: () -> Unit,
    onNameChange: (String) -> Unit,
    notebooks: List<Notebook>,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 3.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your Notebooks",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 3.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Button(onClick = { /*TODO*/ }) { // will call the sign out function, or we could call the navhost to go back to the sign in page and immediately call the sign out function
                        Text(text = "Sign Out")
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onCreateNotebookClicked() }, //Calls the passed in function for viewmodel to create a notebook
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Create Notebook")
                }

                Button(
                    onClick = { onDeleteNotebookClicked() }, // calls the passed in function for the viewmodel to delete a notebook
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Delete Notebook")
                }
            }

    if (uiState.showCreate) {
        CreateNewNotebook(
            uiState = NotebooksUiState(),
            onDismissRequest = onCancelNb,
            onNameChange = onNameChange,
            userIn = uiState.notebookName
        )
    }

    if(uiState.showDelete){
        if (notebooks.isNotEmpty()) {
            DeleteNotebook(
                onDismissRequest = onCancelNb,
                uiState = NotebooksUiState(),
            )
        }
        else {
            Toast.makeText(context , "No notebooks to delete!", Toast.LENGTH_SHORT).show()
        }
    }
        }
    }
}

@Composable
fun CreateNewNotebook(
    onDismissRequest: () -> Unit,
    uiState: NotebooksUiState,
    onNameChange: (String) -> Unit,
    userIn: String
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {},
                enabled = uiState.notebookName.isNotBlank()
            ) {
                Text(text = "Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest ) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Create new notebook") },
        text = {
            Column {
                Text(text = "Enter notebook name:", modifier = Modifier.padding(bottom = 8.dp))
                TextField(
                    value = userIn,
                    onValueChange = onNameChange,
                    singleLine = true
                )
            }
        },
    )
}

@Composable
fun DeleteNotebook(
    onDismissRequest: () -> Unit,
    uiState: NotebooksUiState,
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {/*TODO*/}) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest ){
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNotebook() {
    GetNotedTheme {
        NotebooksPage(
            onNotebookClicked = { },
            onCreateNotebookClicked = {},
            onNameChange = {},
            onDeleteNotebookClicked = {},
            uiState = NotebooksUiState(),
            onCancelNb = {},
            notebooks = emptyList())
    }
}
