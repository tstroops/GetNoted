package com.example.getnoted.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

lateinit var supabase: io.github.jan.supabase.SupabaseClient

fun initSupabase() {
    supabase = createSupabaseClient(
        supabaseUrl = "https://zcgwwbwhkwhkcfypdhgq.supabase.co",
        supabaseKey = "sb_publishable_9ZS2S98VDJ7oJBShVZX9UQ_2cs2b-ze"
    ) {
        install(Postgrest)
        install(Auth)
    }
}

