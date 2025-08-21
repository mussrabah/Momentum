import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            
            // compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)


            
            api(project(":core:presentation"))
            api(project(":core:data"))
            api(project(":core:domain"))
            api(project(":core:common"))
//            api(project(":feature:auth"))
//            api(project(":feature:projects"))
//            api(project(":feature:analytics"))
//            api(project(":feature:notes"))
//            api(project(":feature:pomodoro"))
//            api(project(":feature:tasks"))
            api(project(":feature:analytics:data"))
            api(project(":feature:analytics:domain"))
            api(project(":feature:analytics:presentation"))


            api(project(":feature:auth:data"))
            api(project(":feature:auth:domain"))
            api(project(":feature:auth:presentation"))


            api(project(":feature:notes:data"))
            api(project(":feature:notes:domain"))
            api(project(":feature:notes:presentation"))


            api(project(":feature:pomodoro:data"))
            api(project(":feature:pomodoro:domain"))
            api(project(":feature:pomodoro:presentation"))


            api(project(":feature:projects:data"))
            api(project(":feature:projects:domain"))
            api(project(":feature:projects:presentation"))

            
            api(project(":feature:tasks:data"))
            api(project(":feature:tasks:domain"))
            api(project(":feature:tasks:presentation"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }

        iosMain.dependencies { 

        }
    }
}

android {
    namespace = "com.muss_coding.momentum"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.muss_coding.momentum"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.muss_coding.momentum.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.muss_coding.momentum"
            packageVersion = "1.0.0"
        }
    }
}
