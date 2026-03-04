package com.example.getnoted.ui

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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.GetNotedTheme

@Composable
fun NotebooksPage(
    onNotebookClicked: (Int) -> Unit,
    notebooks: Array<Int>, // we will use UI state, as that will hold the list of notebooks, just a placeholder for UI testing
    modifier: Modifier = Modifier
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
                    Button(onClick = { /*TODO*/ }) { // will call the sign out function, or we could call the navhost to go back to the sing in page and immediatly call the signout function
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
                    onClick = {/*TODO*/ }, //Calls the passed in function for viewmodel to create a notebook
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Create Notebook")
                }

                Button(
                    onClick = {/*TODO*/ }, // calls the passed in function for the viewmodel to delete a notebook
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Delete Notebook")
                }
            }


            val chunkedNotebooks = notebooks.toList().chunked(3)
            
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in chunkedNotebooks) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (notebook in row) {
                            Button(
                                onClick = {onNotebookClicked(notebook)}, // navigate to that notebooks note page
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Notebook $notebook")
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
fun PreviewNotebook() {
    GetNotedTheme {
        NotebooksPage(onNotebookClicked = { }, notebooks = arrayOf(1, 2, 3, 4, 5))
    }
}
