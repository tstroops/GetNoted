package com.example.getnoted.ui

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.GetNotedTheme

@Composable
fun SignUpPage(
    onBackClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var username by remember{ mutableStateOf("") }
    var passwordOne by remember { mutableStateOf("") }
    var passwordTwo by remember { mutableStateOf("") }

    Button(modifier = Modifier.padding(top=25.dp, start = 10.dp),
        onClick = onBackClicked
    ) {
        Text(text = "<")
    }
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserSignUp(
            label = "Username",
            userIn = username,
            onValueChange = {username = it}
        )
        UserSignUp(
            label = "Password",
            userIn = passwordOne,
            onValueChange = {passwordOne = it}
        )
        UserSignUp(
            label = "Confirm Password",
            userIn = passwordTwo,
            onValueChange = {passwordTwo = it}
        )
        Button(onClick = {
            if(passwordOne.length<8){
                Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            }
            else if(passwordOne != passwordTwo){
                Toast.makeText(context, "Passwords must match", Toast.LENGTH_SHORT).show()
            }
            else{
                onSignUpClicked()
                Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            }
        }) {
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
        value = userIn,
        onValueChange = onValueChange,
        label = {Text(label)},
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    GetNotedTheme {
        SignUpPage(
            onBackClicked = {},
            onSignUpClicked = {}
        )
    }
}