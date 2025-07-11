import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "nl.codingwithlinda.notemark"
    compileSdk = 36

    defaultConfig {
        applicationId = "nl.codingwithlinda.notemark"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) localPropertiesFile.inputStream().use { localProperties.load(it) }
        buildConfigField("String", "AUTH_API_EMAIL", "\"${localProperties.getProperty("AUTH_API_EMAIL")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":persistence_room"))

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(composeBom)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.serialization.json)

    //navigation2
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.ui.ktx)
    //implementation("androidx.navigation:navigation-common:2.9.0")

    // Core runtime for Jetpack Navigation 3 library — provides navigation components and APIs
    implementation("androidx.navigation3:navigation3-runtime:1.0.0-alpha04")
    // UI components for Navigation 3 — includes NavDisplay etc.
    implementation("androidx.navigation3:navigation3-ui:1.0.0-alpha04")
    // ViewModel integration with Navigation 3 — provides lifecycle-aware ViewModels scoped to navigation destinations
    implementation("androidx.lifecycle:lifecycle-viewmodel-navigation3:1.0.0-alpha02")

    //ui
    implementation(libs.androidx.adaptive.android)
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    //splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    //datastore
    implementation("androidx.datastore:datastore:1.1.7")

    //date time
    //backwards compatible date time library that supports older devices than 26
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.6")

    //ktor
    implementation(libs.ktor.client.auth)
    val ktor_version = "3.0.0"
    implementation ("io.ktor:ktor-client-core:$ktor_version")
    implementation ("io.ktor:ktor-client-android:$ktor_version")
    implementation ("io.ktor:ktor-client-cio:$ktor_version")
    implementation ("io.ktor:ktor-client-content-negotiation:$ktor_version")
    //implementation ("io.ktor:ktor-client-serialization:$ktor_version")
    implementation ("io.ktor:ktor-serialization-kotlinx-json:${ktor_version}")
    //implementation("io.ktor:ktor-client-resources:${ktor_version}")
    implementation ("io.ktor:ktor-client-logging:$ktor_version")
    implementation ("ch.qos.logback:logback-classic:1.2.3")



    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.assertk)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}