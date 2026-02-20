package com.example.getnoted.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun NotesPage(
    onNoteClicked: ()-> Unit,
    notes: Array<Int>
){
    Column {
        Text(text = "Notes")
        for (note in notes){
            val noteID = note
            Button(onClick = onNoteClicked) {
                Text(text = "Note $note")
            }
        }
        Button(onClick = {/*TODO*/ }) {
            Text(text = "New Note")
        }
        Button(onClick = {/*TODO*/ }) {
            Text(text = "Delete Note")
        }
    }
}