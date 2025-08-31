package com.muss_coding.momentum.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.muss_coding.core.common.navigation.Routes
import com.muss_coding.momentum.navigation.authNavigation
import com.muss_coding.momentum.navigation.mainNavigation
import com.muss_coding.momentum.navigation.onboardingNavigation

@Composable
fun Root(
    windowSizeClass: String?,
    modifier: Modifier,
    isLoggedInPreviously: Boolean,
    shouldShowOnboarding: Boolean,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isLoggedInPreviously) Routes.MainGraph
                            else if (shouldShowOnboarding) Routes.OnboardingGraph
                            else Routes.AuthGraph
    ) {
        onboardingNavigation(
            windowSizeClass = windowSizeClass,
            navController = navController
        )

        authNavigation(
            windowSizeClass = windowSizeClass,
            navController = navController
        )

        mainNavigation(
            windowSizeClass = windowSizeClass,
            navController = navController
        )
    }
}