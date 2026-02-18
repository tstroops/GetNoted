package com.example.getnoted.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun notebook(text:String){
    val notebookNum = 3
    Column {
        for (i in 1..notebookNum){
            Button(onClick = {/*TODO*/}) {
                Text(text = "$text $i")
            }
        }
    }
}

@Preview
@Composable
fun PreviewNotebook(){
    notebook("Notebook")
}