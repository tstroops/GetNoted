package com.example.getnoted.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeScreen(
    onSignUpClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    /**
     * @param onSignUpClicked navigates to the sign-up screen
     * @param onSignInClicked navigates to the sign in screen
     * creates the welcome screen with the sign in and sign up buttons
     */

    //column needed to look pretty
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        //calls navigation to sign in
        Button(onClick = onSignInClicked) {
            Text(text ="Sign In")
        }

        //looks nice
        Spacer(modifier= Modifier.height(16.dp))

        //calls navigation to sign-up
        Button(onClick = onSignUpClicked) {
            Text(text ="Sign Up")
        }
    }
}