package com.muss_coding.momentum.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muss_coding.core.common.navigation.Routes
import com.muss_coding.feature.onboarding.presentation.introduction.IntroductionScreen
import com.muss_coding.feature.onboarding.presentation.welcome.WelcomeScreen

@Composable
fun Root(
    windowSizeClass: String?,
    startDestination: Routes,
    modifier: Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.Welcome> {
            WelcomeScreen(
                onNextClick = {
                    navController.navigate(Routes.Introduction)
                }
            )
        }

        composable<Routes.Introduction> {
            IntroductionScreen(
                onNextClick = {
                    navController.navigate(Routes.Introduction)
                }
            )
        }

        composable<Routes.Login> {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello, You're logged in you MotherFucker",
                    //fontSize = 32.sp,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}