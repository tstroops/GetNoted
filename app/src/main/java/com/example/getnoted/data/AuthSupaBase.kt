package com.example.getnoted.data

import android.provider.ContactsContract
import io.github.jan.supabase.auth.*
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object AuthSupaBase {

    suspend fun signUp(email: String, password: String) {

            val user = supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            println("Signed up $user")
    }


    suspend fun signIn(email: String, password: String) {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password

            }
    }
}
// Might need coroutines or another thread to launch, since Sign in and sign out functions
// in supabase are suspended which means they wait for a response, like a network response
// from supabase. In order to properly call the functions, it needs to be in a coroutine/ lightweight
// separate thread so that it can wait for the response from supabase without pausing the main thread
// to reduce lag and crashes which is built into the supabase kotlin client functions