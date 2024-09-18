plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.nightcode.pokedex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nightcode.pokedex"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    flavorDimensions.add("env")
    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            resValue("string", "app_name", "Pokedex Dev")
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/api/v2/\"")
        }
        create("prod") {
            dimension = "env"
            resValue("string", "app_name", "Pokedex")
            buildConfigField("String", "BASE_URL", "\"https://pokeapi.co/api/v2/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    detekt {
        buildUponDefaultConfig = true // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        config.setFrom("$rootDir/detekt.yml")
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.3.0")
        debug.set(false)
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        enableExperimentalRules.set(false)
        ignoreFailures.set(false)
        additionalEditorconfig.set(
            mapOf(
                "insert_final_newline" to "false",
            ),
        )
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.timber)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.coil.compose)
    implementation(libs.androidx.animation)
    implementation(libs.kmpalette.core)
    implementation(libs.kmpalette.extensions.network)
    implementation(libs.ktor.client.android)
    implementation(libs.rebugger)
    implementation(libs.androidx.datastore.preferences)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}