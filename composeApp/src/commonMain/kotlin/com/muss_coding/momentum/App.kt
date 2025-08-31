package com.muss_coding.momentum

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muss_coding.core.presentation.ui.theme.MomentumTheme
import com.muss_coding.momentum.root.Root
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MomentumTheme {
        //val windowSizeClass = calculateWindowSizeClass()
        Scaffold {
            Root(
                modifier = Modifier.padding(it),
                windowSizeClass = null,
                isLoggedInPreviously = false,
                shouldShowOnboarding = true
            )
        }
    }
}