package com.example.getnoted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.GetNotedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetNotedTheme {
                Greeting(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center))
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var result = 1
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {result=(1..6).random()}) {
            Text(text ="Sign In")
        }
        Spacer(modifier= Modifier.height(16.dp))
        Button(onClick = {result=(1..6).random()}) {
            Text(text ="Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting()
    }