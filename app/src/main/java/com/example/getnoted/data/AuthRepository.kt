package com.example.getnoted.data

import io.github.jan.supabase.auth.*
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession

object AuthRepository {

    suspend fun signUp(email: String, password: String) {
        /**
         * @param email the email of the user
         * @param password the password of the user
         * @return none
         * takes a user's email and password and creates a new user account in supabaseAuth
         */
        val user = supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
        println("Signed up $user")
    }

    suspend fun signIn(email: String, password: String) {
        /**
         * @param email the email of the user
         * @param password the password of the user
         * @return none
         * authenticates a user with their email and password via supabaseAuth
         */
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    fun getCurrentSession(): UserSession?
    {
        /**
         * @return none
         * retrieves the user session if there is one
         */
        val session = supabase.auth.currentSessionOrNull()
        return session
    }

    suspend fun signOut()
    {
        /**
         * @return none
         * has supabaseAuth sign the user out
         */
        supabase.auth.signOut()

    }

    suspend fun restoreSession() {
        /**
         * @return none
         * restores user session if there is one
         */
        supabase.auth.loadFromStorage()
    }
}
