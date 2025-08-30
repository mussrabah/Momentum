package com.muss_coding.feature.onboarding.presentation.introduction

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IntroductionScreen(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit
) {
    Column {
        Text(
            text = "Introduction Screen",
            style = MaterialTheme.typography.displayLarge
        )
    }
}