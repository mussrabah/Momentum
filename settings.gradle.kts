rootProject.name = "Momentum"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}
//  ------------------------------------------------------------------------
//  |                                                                      |
//  |                                                                      |
//  ------------------------------------------------------------------------
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")
include(":core")
include(":feature")
include(":core:presentation")
include(":core:data")
include(":core:domain")
include(":core:common")
include(":feature:auth")
include(":feature:projects")
include(":feature:analytics")
include(":feature:notes")
include(":feature:pomodoro")
include(":feature:tasks")
include(":feature:analytics:data")
include(":feature:analytics:domain")
include(":feature:analytics:presentation")
include(":feature:auth:data")
include(":feature:auth:domain")
include(":feature:auth:presentation")
include(":feature:notes:data")
include(":feature:notes:domain")
include(":feature:notes:presentation")
include(":feature:pomodoro:data")
include(":feature:pomodoro:domain")
include(":feature:pomodoro:presentation")
include(":feature:projects:data")
include(":feature:projects:domain")
include(":feature:projects:presentation")
include(":feature:tasks:data")
include(":feature:tasks:domain")
include(":feature:tasks:presentation")
include(":feature:onboarding")
include(":feature:onboarding:presentation")
