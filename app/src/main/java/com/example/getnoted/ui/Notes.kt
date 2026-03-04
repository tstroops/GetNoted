package com.example.getnoted.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.GetNotedTheme
import kotlin.collections.chunked

@Composable
fun NotesPage(
    onNoteClicked: (Int)-> Unit,
    notes: Array<Int>, // Just placeholder, will not be array, we will pass in the UIstate to get the list of notes
    modifier: Modifier = Modifier
){
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
                        text = "Your Notes", // Maybe we insert the UI state name for the notebook, like "Notebook title" Notes
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
                    Button(onClick = { /*TODO*/ }) { // The back button so if back clicked, call navhost
                        Text(text = "Back")
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
                    onClick = {/*TODO*/ }, // call function thats passed in that calls to create note object, then add it to list of notes
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Create Note")
                }

                Button(
                    onClick = {/*TODO*/ }, // Same thing call pass in function that calls viewmodel for delete notebook
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Delete Note")
                }
            }

            val chunkedNotes = notes.toList().chunked(3)

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in chunkedNotes) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (note in row) {
                            Button(
                                onClick = {onNoteClicked(note)}, // this will navigate to that note page
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Notebook $note")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotes() {
    GetNotedTheme {
        NotesPage(onNoteClicked = { }, notes = arrayOf(1, 2, 3, 4, 5)) // same here just for testing, we will use UI state for list of notes
    }
}