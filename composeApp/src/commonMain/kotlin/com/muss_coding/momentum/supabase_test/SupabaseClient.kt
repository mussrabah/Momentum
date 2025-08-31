package com.muss_coding.momentum.supabase_test

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

// You get these from your Supabase project settings
private const val SUPABASE_URL = "https://tcocoicxgtijepmaevqg.supabase.co"
private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRjb2NvaWN4Z3RpamVwbWFldnFnIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTY2MDMxNTcsImV4cCI6MjA3MjE3OTE1N30.ru_6pNShwWWKdR6EXmzyp2-WiCX7p6gbW-2sskULdUM"

object SupabaseManager {

    val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
        install(Realtime)
    }
}