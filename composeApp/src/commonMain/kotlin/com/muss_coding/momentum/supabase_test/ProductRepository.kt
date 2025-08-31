package com.muss_coding.momentum.supabase_test

import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {

    private val dbClient = SupabaseManager.supabase.postgrest

    suspend fun getProducts(): Result<List<Product>> = withContext(Dispatchers.Default) {
        try {
            val products = dbClient.from("products").select().decodeList<Product>()
            Result.success(products)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addProduct(product: Product): Result<Unit> = withContext(Dispatchers.Default) {
        try {
            dbClient.from("products").insert(product)
            Result.success(Unit)
        } catch(e: Exception) {
            Result.failure(e)
        }
    }
}