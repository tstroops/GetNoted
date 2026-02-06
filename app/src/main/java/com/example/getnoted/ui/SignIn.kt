package com.example.getnoted.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.GetNotedTheme

@Composable
fun SignInPage(
    onBackClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    modifier: Modifier = Modifier) {
    var username by remember{ mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Button(modifier = Modifier.padding(top=25.dp, start = 10.dp),
        onClick = onBackClicked
    ) {
        Text(text = "<")
    }

    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserSignIn(label = "Username", userIn = username, onValueChange = {username = it})
        UserSignIn(label = "Password", userIn = password, onValueChange = {password = it})
        Button(onClick = onSignInClicked){
            Text(text = "Sign In")
        }

    }
}

@Composable
fun UserSignIn(
    label: String,
    userIn: String,
    onValueChange: (String)->Unit,
    modifier: Modifier = Modifier
){
    Text( text = label)
    TextField(
        value = userIn,
        onValueChange = onValueChange,
        label = {Text(label)},
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    GetNotedTheme {
        SignInPage(
            onBackClicked = {},
            onSignInClicked = {}
        )
    }
}