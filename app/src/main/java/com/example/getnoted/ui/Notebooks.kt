package com.example.getnoted.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NotebooksPage(
        onNotebookClicked: ()->Unit,
        notebooks: Array<Int>
){
    Column {
        Text(text ="Notebooks")
        for (notebook in notebooks){
            val notebookID = notebook
            Button(onClick = onNotebookClicked) {
                Text(text = "Notebook $notebook")
            }
        }
        Button(onClick = {/*TODO*/ }) {
            Text(text = "New Notebook")
        }
        Button(onClick = {/*TODO*/ }) {
            Text(text = "Delete Notebook")
        }
    }
}

@Preview
@Composable
fun PreviewNotebook(){
    NotebooksPage(onNotebookClicked = {/*TODO*/}, notebooks = arrayOf(1,2))
}