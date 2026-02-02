package com.example.getnoted

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.platform.LocalContext
import com.example.getnoted.ui.theme.GetNotedTheme

class SignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetNotedTheme {
                SignUpPage(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun SignUpPage(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var username by remember{ mutableStateOf("") }
    var passwordOne by remember { mutableStateOf("") }
    var passwordTwo by remember { mutableStateOf("") }

    Button(modifier = Modifier.padding(top=25.dp, start = 10.dp),onClick = {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }) {
        Text(text = "<")
    }
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserSignUp(label = "Username", userIn = username, onValueChange = {username = it})
        UserSignUp(label = "Password", userIn = passwordOne, onValueChange = {passwordOne = it})
        UserSignUp(label = "Confirm Password", userIn = passwordTwo, onValueChange = {passwordTwo = it})
        Button(onClick = {/*TODO*/}){
            Text(text = "Sign Up")
        }
    }

}
@Composable
fun UserSignUp(
    label: String,
    userIn: String,
    onValueChange: (String)->Unit,
    modifier: Modifier = Modifier
){
    Text( text = label)
    TextField(
        value = "",
        onValueChange = onValueChange,
        label = {Text(label)},
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    GetNotedTheme {
        SignUpPage()
    }
}