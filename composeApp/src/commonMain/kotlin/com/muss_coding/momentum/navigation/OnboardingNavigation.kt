package com.muss_coding.momentum.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.muss_coding.core.common.navigation.Routes
import com.muss_coding.feature.onboarding.presentation.introduction.IntroductionScreen
import com.muss_coding.feature.onboarding.presentation.unleash_deep_work_screen.UnleashDeepWorkScreen
import com.muss_coding.feature.onboarding.presentation.welcome.WelcomeScreen

fun NavGraphBuilder.onboardingNavigation(
    windowSizeClass: String?,
    navController: NavHostController,
) {
    navigation<Routes.OnboardingGraph>(
        startDestination = Routes.Welcome,
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
                    navController.navigate(Routes.UnleashDeepWork)
                }
            )
        }

        composable<Routes.UnleashDeepWork> {
            UnleashDeepWorkScreen(
                onNextClick = {
                    navController.navigate(Routes.Introduction)
                }
            )
        }
    }
}