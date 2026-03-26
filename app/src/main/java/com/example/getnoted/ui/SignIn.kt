package com.example.getnoted.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getnoted.ui.theme.Black
import com.example.getnoted.ui.theme.GetNotedTheme
import com.example.getnoted.viewModel.AuthUiState

@Composable
fun SignInPage(
    uiState: AuthUiState, //handles the supabase Auth
    onEmailChange: (String) -> Unit, //specifically for the user updates to email
    onPasswordChange: (String) -> Unit, //specifically for the user updates to the password
    onBackClicked: () -> Unit, //returns to the welcome screen
    onSignInClicked: () -> Unit, //calls the sign in
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        //Back button
        Button(
            modifier = Modifier.padding(top = 25.dp, start = 10.dp),
            onClick = onBackClicked
        ) {
            Text(text = "<")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Email field for sign in
            UserSignIn(
                label = "Email",
                userIn = uiState.email,
                onValueChange = onEmailChange
            )

            //Password field for sign in
            UserSignIn(
                label = "Password",
                userIn = uiState.password,
                onValueChange = onPasswordChange,
                visualTransformation = VisualTransformation { text ->
                    TransformedText(
                        AnnotatedString("*".repeat(text.text.length)), //hides the password
                        OffsetMapping.Identity
                    )
                }
            )

            //button that calls the sign in
            Button(onClick = onSignInClicked) {
                Text(text = "Sign In")
            }
        }
    }
}

@Composable
fun UserSignIn(
    label: String,
    userIn: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    /**
     * @param label determines the expected entry for the field
     * @param userIn the user entered information
     * @param onValueChange function that handles the user updates to the text field
     * creates a text field for the user to enter the expected information
     */
    Text(text = label)
    TextField(
        value = userIn,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Black,
            unfocusedTextColor = Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
        )
    )
    //spacer added to look nice
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    GetNotedTheme {
        SignInPage(
            uiState = AuthUiState(email = "test123@example.com"),
            onEmailChange = {},
            onPasswordChange = {},
            onBackClicked = {},
            onSignInClicked = {}
        )
    }
}