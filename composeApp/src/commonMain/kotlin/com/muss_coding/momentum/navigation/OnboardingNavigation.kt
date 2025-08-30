package com.muss_coding.momentum.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingNavigation(
    windowSizeClass: String?,
) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Hello, Compose MultiPlatform,",
                    fontWeight = Bold,
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            }
}