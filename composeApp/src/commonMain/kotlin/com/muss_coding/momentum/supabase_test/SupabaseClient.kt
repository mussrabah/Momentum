package com.muss_coding.momentum.supabase_test

import com.muss_coding.config.BuildKonfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

private val SUPABASE_URL = BuildKonfig.SUPABASE_URL
private val SUPABASE_KEY = BuildKonfig.SUPABASE_KEY

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