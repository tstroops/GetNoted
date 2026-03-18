package com.example.getnoted.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.GetNotedTheme
import com.example.getnoted.viewModel.Notebook
import com.example.getnoted.viewModel.NotebooksUiState

@Composable
fun NotebooksPage(
    uiState: NotebooksUiState,
    onNotebookClicked: () -> Unit,
    onCreateNotebookClicked: () -> Unit,
    onDeleteNotebookClicked: () -> Unit,
    onCreateConfirm: () -> Unit,
    onDeleteConfirm: () ->Unit,
    onCancelNb: () -> Unit,
    onNameChange: (String) -> Unit,
    notebooks: List<Notebook>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

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
                    Button(onClick = { /* Sign Out logic */ }) {
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
                .padding(16.dp)
        ) {
            // Notebooks Grid Area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val chunkedNotebooks = notebooks.chunked(3)
                for (row in chunkedNotebooks) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (notebook in row) {
                            Button(
                                onClick = { onNotebookClicked() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = notebook.title)
                            }
                        }
                        // Fill empty slots in the row if needed to keep buttons size consistent
                        repeat(3 - row.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onCreateNotebookClicked() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text(text = "Create Notebook")
                }

                Button(
                    onClick = { onDeleteNotebookClicked() },
                    modifier = Modifier.weight(1f),
                    colors = ButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text(text = "Delete Notebook")
                }
            }
        }

        if (uiState.showCreate) {
            CreateNewNotebook(
                onDismissRequest = onCancelNb,
                onNameChange = onNameChange,
                onCreateConfirm = onCreateConfirm,
                userIn = uiState.notebookName
            )
        }

        if (uiState.showDelete) {
            if (notebooks.isNotEmpty()) {
                DeleteNotebook(
                    onDismissRequest = onCancelNb,
                    onDeleteConfirm = onDeleteConfirm
                )
            } else {
                Toast.makeText(context, "No notebooks to delete!", Toast.LENGTH_SHORT).show()
                onCancelNb() // Reset the state since we can't show the dialog
            }
        }
    }
}

@Composable
fun CreateNewNotebook(
    onDismissRequest: () -> Unit,
    onNameChange: (String) -> Unit,
    onCreateConfirm: () -> Unit,
    userIn: String
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onCreateConfirm ,
                enabled = userIn.isNotBlank()
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
    onDeleteConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onDeleteConfirm() }) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete Notebook") },
        text = { Text(text = "Are you sure you want to delete this notebook?") }
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
            onCreateConfirm = {},
            onDeleteConfirm = {},
            uiState = NotebooksUiState(),
            onCancelNb = {},
            notebooks = mutableListOf(Notebook(title = "Work", userId = -1), Notebook(title = "Home", userId = -2))
        )
    }
}
