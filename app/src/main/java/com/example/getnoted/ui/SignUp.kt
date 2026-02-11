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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.viewModel.SignUpViewModel
import com.example.getnoted.ui.theme.GetNotedTheme

private val viewModel = SignUpViewModel()



@Composable
fun SignUpPage(
    onBackClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

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
            label = "Email",
            userIn = viewModel.email,
            onValueChange = {viewModel.updateEmail(it)}
        )
        UserSignUp(
            label = "Password",
            userIn = viewModel.password,
            onValueChange = {viewModel.updatePassword(it)}
        )
        UserSignUp(
            label = "Confirm Password",
            userIn = viewModel.passwordConfirm,
            onValueChange = {viewModel.updatePasswordConfirm(it)}
        )
        Button(onClick = {
            if(viewModel.password.length<8){
                Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            }
            else if(viewModel.password != viewModel.passwordConfirm){
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