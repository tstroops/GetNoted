package com.example.getnoted.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.viewModel.Notebook
import com.example.getnoted.viewModel.NotebooksUiState

@Composable
fun NotebooksPage(
    uiState: NotebooksUiState,
    onNotebookClicked: () -> Unit,
    onCreateNotebookClicked: () -> Unit,
    onNameChange: (String) -> Unit,
    onDeleteNotebookClicked: () -> Unit,
    notebooks: Array<Notebook>
) {
    Column {
        Text(text = "Notebooks", modifier = Modifier.padding(16.dp))
        
        for (notebook in uiState.notebooks) {
            Button(onClick = onNotebookClicked) {
                Text(text = notebook.title)
            }
        }

        Button(onClick = { onCreateNotebookClicked() }) {
            Text(text = "New Notebook")
        }

        Button(onClick = { onDeleteNotebookClicked() }) {
            Text(text = "Delete Notebook")
        }
    }

    if (uiState.showCreate) {
        CreateNewNotebook(
            uiState = NotebooksUiState(),
            onDismissRequest = { onCreateNotebookClicked() },
            onNameChange = onNameChange,
            userIn = uiState.notebookName
        )
    }

    if(uiState.showDelete){
        DeleteNotebook(
            onDismissRequest = { onDeleteNotebookClicked() },
            uiState = NotebooksUiState(),
        )
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
            TextButton(onClick = onDismissRequest) {
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
            TextButton(onClick = {/*TODO*/}){
                Text(text = "Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNotebook() {
    NotebooksPage(
        uiState = NotebooksUiState(),
        onCreateNotebookClicked = {},
        onNotebookClicked = {},
        onNameChange = {},
        onDeleteNotebookClicked = {},
        notebooks = emptyArray()
    )
}
