package com.muss_coding.momentum.supabase_test


import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {

    private val authClient = SupabaseManager.supabase.auth

    suspend fun signUp(email: String, password: String): Result<Unit> = withContext(Dispatchers.Default) {
        try {
            authClient.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<Unit> = withContext(Dispatchers.Default) {
        try {
            authClient.signInWith(Email) {
                this.email = email
                this.password = password
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser() = authClient.currentUserOrNull()

    suspend fun signOut() {
        authClient.signOut()
    }
}