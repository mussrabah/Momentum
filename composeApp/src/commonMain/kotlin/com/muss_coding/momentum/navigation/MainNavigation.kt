package com.muss_coding.momentum.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.muss_coding.core.common.navigation.Routes

fun NavGraphBuilder.mainNavigation(
    windowSizeClass: String?,
    navController: NavHostController,
) {
    navigation<Routes.MainGraph>(
        startDestination = Routes.Projects,
    ) {
        composable<Routes.Projects> {

        }

        composable<Routes.Tasks> {

        }

        composable<Routes.Notes> {

        }

        composable<Routes.Pomodoro> {

        }

        composable<Routes.Analytics> {

        }
    }
}