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
import com.example.getnoted.viewModel.Note
import com.example.getnoted.viewModel.NotesUiState
import kotlin.collections.chunked

@Composable
fun NotesPage(
    uiState: NotesUiState,
    onNoteClicked: (Note)-> Unit,
    onCreateNoteClicked: () -> Unit, //used for displaying the create dialog
    onDeleteNoteClicked: () -> Unit, //used for displaying the delete dialog
    onConfirmCreate: () -> Unit, //creates the note
    onConfirmDelete: () -> Unit, //deletes the note
    onCancelNote: ()-> Unit, //cancels the dialog
    onBackClicked: ()-> Unit, //returns to the notebooks screen
    onNameChange: (String) -> Unit, //for the text field in the dialog
    notes: List<Note>, //for displaying the notes obtained from supabase
    modifier: Modifier = Modifier
){

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
                        text = "Your Notes",
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
                    //Back button
                    Button(onClick = onBackClicked ) {
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
                .padding(16.dp)
        ) {
            // Notes Grid Area
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val chunkedNotes = notes.chunked(3) //sets the number of notes per row
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
                                Text(text = note.name)
                            }
                        }

                        repeat(3-row.size) { // Fill empty slots in the row if needed to keep buttons size consistent
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //launches the create dialog
                Button(
                    onClick = onCreateNoteClicked,
                    modifier = Modifier.weight(1f),
                    colors = ButtonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text(text = "Create Note")
                }

                //launches the delete dialog
                Button(
                    onClick =  onDeleteNoteClicked,
                    modifier = Modifier.weight(1f),
                    colors = ButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Text(text = "Delete Note")
                }
            }
        }

        //handles the create dialog display
        if(uiState.showCreate){
            CreateNewNote(
                onDismissRequest = onCancelNote,
                uiState = uiState,
                onNameChange = onNameChange,
                userIn = uiState.noteName,
                onConfirmCreate = onConfirmCreate
            )
        }

        //handles the delete dialog display
        if(uiState.showDelete){
            if (notes.isNotEmpty()) {
                DeleteNote(
                    onDismissRequest = onCancelNote,
                    onConfirmDelete = onConfirmDelete
                )
            }
            else{
                //case for empty list of notes
                Toast.makeText(context, "No notes to delete!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun CreateNewNote(
    onDismissRequest: () -> Unit,
    uiState: NotesUiState,
    onNameChange: (String) -> Unit,
    userIn: String,
    onConfirmCreate: () -> Unit,
){
    /**
     * @param onDismissRequest dismisses the dialog
     * @param onNameChange changes the name of the note
     * @param onConfirmCreate creates the note
     * @param userIn the name of the note
     * creates a note with the title the user entered
     * the user must enter a title name to create a note
     */
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onConfirmCreate() },
                enabled = uiState.noteName.isNotBlank()) {
                Text(text = "Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest){
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Create new note")},
        text = {
            Column {
                Text(text = "Enter note name:", modifier = Modifier.padding(bottom = 8.dp))
                TextField(
                    value = userIn,
                    onValueChange = onNameChange,
                    singleLine = true
                )
            }
        }
    )
}

@Composable
fun DeleteNote(
    onDismissRequest: () -> Unit,
    onConfirmDelete: () -> Unit,
){
    /**
     * @param onDismissRequest dismisses the dialog
     * @param onConfirmDelete deletes the note
     * deletes the note the user last clicked
     */
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = { onConfirmDelete() }){
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest){
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "Delete Note") },
        text = { Text(text = "Are you sure you want to delete this note?") }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNotes() {
    GetNotedTheme {
        NotesPage(
            onNoteClicked = { },
            onCreateNoteClicked = {},
            onDeleteNoteClicked = {},
            onCancelNote = {},
            onNameChange = {},
            onBackClicked = {},
            onConfirmCreate = {},
            onConfirmDelete = {},
            uiState = NotesUiState(),
            notes = mutableListOf(Note(name = "Work", info = "Work", nbId = 1, id = 0), Note(name = "Home", info = "Home", nbId = 1, id = 1))
        )
    }
}