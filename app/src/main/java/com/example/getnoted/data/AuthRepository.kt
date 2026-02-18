package com.example.getnoted.data

import io.github.jan.supabase.auth.*
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession

object AuthRepository {

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

    suspend fun getCurrentSession(): UserSession?
    {
        val session = supabase.auth.currentSessionOrNull()
        return session
    }

    suspend fun signOut()
    {
        supabase.auth.signOut()

    }
}
