package com.muss_coding.core.common.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {

    // Onboarding routes
    @Serializable
    object OnboardingGraph : Routes

    @Serializable
    object Welcome : Routes

    @Serializable
    object Introduction: Routes

    @Serializable
    object UnleashDeepWork: Routes

    // Auth routes
    @Serializable
    object AuthGraph : Routes

    @Serializable
    object Login : Routes

    @Serializable
    object Register: Routes

    // Main routes


    @Serializable
    object MainGraph : Routes

    @Serializable
    object Analytics : Routes

    @Serializable
    object Notes : Routes

    @Serializable
    object Pomodoro : Routes

    @Serializable
    object Projects : Routes

    @Serializable
    object Tasks : Routes
}