package com.example.getnoted.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.getnoted.ui.theme.GetNotedTheme
import com.example.getnoted.viewmodel.AuthUiState


@Composable
fun SignUpPage(
    uiState: AuthUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onBackClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Wrap everything in a Box so the Button can sit on top of the centered Column
    Box(modifier = modifier.fillMaxSize()) {
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
            UserSignUp(
                label = "Email",
                userIn = uiState.email,
                onValueChange = onEmailChange
            )
            UserSignUp(
                label = "Password",
                userIn = uiState.password,
                onValueChange = onPasswordChange
            )
            UserSignUp(
                label = "Confirm Password",
                userIn = uiState.passwordConfirm,
                onValueChange = onPasswordConfirmChange
            )
            Button(onClick = {
                if (uiState.password.length < 8) {
                    Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                } else if (uiState.password != uiState.passwordConfirm) {
                    Toast.makeText(context, "Passwords must match", Toast.LENGTH_SHORT).show()
                } else {
                    onSignUpClicked()
                }
            }) {
                Text(text = "Sign Up")
            }
        }
    }
}

@Composable
fun UserSignUp(
    label: String,
    userIn: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = label)
    TextField(
        value = userIn,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    GetNotedTheme {
        SignUpPage(
            uiState = AuthUiState(email = "test123@example.com"),
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordConfirmChange = {},
            onBackClicked = {},
            onSignUpClicked = {}
        )
    }
}