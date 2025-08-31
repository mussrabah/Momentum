package com.muss_coding.momentum

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.muss_coding.core.presentation.ui.theme.MomentumTheme
import com.muss_coding.momentum.root.Root
import com.muss_coding.momentum.supabase_test.AuthRepository
import com.muss_coding.momentum.supabase_test.ProductRepository
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MomentumTheme {
        //val windowSizeClass = calculateWindowSizeClass()
        Scaffold {

            val coroutineScope = rememberCoroutineScope()
            val authRepository = AuthRepository()
            val productRepository = ProductRepository()

            coroutineScope.launch {
                authRepository
                    .signIn("admin@gmail.com", "123456").onSuccess {
                        println("Logged in successfully")
                    }.onFailure {
                        println("Logged in failed")
                    }
            }

            coroutineScope.launch {
                productRepository
                    .getProducts().onSuccess { products ->
                        println("Products fetched successfully: $products")
                    }.onFailure {
                        println("Products fetched failed")
                    }
            }
            Root(
                modifier = Modifier.padding(it),
                windowSizeClass = null,
                isLoggedInPreviously = false,
                shouldShowOnboarding = true
            )
        }
    }
}