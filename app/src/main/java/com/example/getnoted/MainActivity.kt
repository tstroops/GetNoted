package com.example.getnoted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.getnoted.data.AuthRepository
import com.example.getnoted.data.initSupabase
import com.example.getnoted.data.supabase
import com.example.getnoted.ui.theme.GetNotedTheme
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSupabase(applicationContext)

        lifecycleScope.launch {
            AuthRepository.restoreSession()
        }

        enableEdgeToEdge()
        setContent {
            GetNotedTheme {
                GetNotedScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GetNotedScreen()
    }