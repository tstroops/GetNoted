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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.Black
import com.example.getnoted.ui.theme.GetNotedTheme

@Composable
fun NotePage(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    text: String // so the text field knows what to display
) {
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
                    //lets the user know they're in the notes screen
                    Text(
                        text = "Note", // We could insert the UI state here to display the name of the note at the top bar
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        //back button
                        Button(onClick = {onSaveClicked(); onBackClicked()}) {
                            Text(text = "Back")
                        }
                        //save button
                        Button(onClick = {onSaveClicked()}) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
        ) {
            TextField(
                value = text, // This will be the UI state for the text
                onValueChange = { onTextChanged(it) }, // Add the function for when the text is changed
                modifier = Modifier.fillMaxSize(),
                placeholder = { Text("Type Here") }, //highlights the text field
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNote() {
    var previewText by remember { mutableStateOf("Test Text") }
    GetNotedTheme {
        NotePage(
            onBackClicked = {},
            onSaveClicked = {},
            onTextChanged = { previewText = it },
            text = previewText
        )
    }
}
