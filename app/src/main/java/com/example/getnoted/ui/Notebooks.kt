package com.example.getnoted.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.getnoted.viewModel.NotebooksUiState
import com.example.getnoted.viewModel.NotebooksViewModel

@Composable
fun NotebooksPage(
        uiState: NotebooksUiState,
        onNotebookClicked: ()->Unit,
        onNewNotebookClicked: ()->Unit,
        onDeleteNotebookClicked: ()->Unit,
        notebooks: Array<Int>
){
    Column {
        Text(text ="Notebooks")
        for (notebook in uiState.notebooks){
            val notebookID = notebook.id
            Button(onClick = onNotebookClicked) {
                Text(text = notebook.name)
            }
        }
        Button(onClick = { onNewNotebookClicked() }) {
            Text(text = "New Notebook")
        }
        Button(onClick = { onDeleteNotebookClicked() }) {
            Text(text = "Delete Notebook")
        }
    }
}

@Preview
@Composable
fun PreviewNotebook(){
    NotebooksPage(
        uiState = NotebooksUiState(),
        onNotebookClicked = {},
        onNewNotebookClicked = {},
        onDeleteNotebookClicked = {},
        notebooks = arrayOf(1,2))
}