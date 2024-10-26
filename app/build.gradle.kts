plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.fanz.oways"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fanz.oways"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation ("androidx.legacy:legacy-support-v4:1.0.0")

    implementation ("com.google.android.material:material:1.12.0")

    implementation("androidx.multidex:multidex:2.0.1")

    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation ("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

    // hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // kotlin coroutines play services
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1")

    // Gson and retrofit
    implementation ("com.google.code.gson:gson:2.11.0")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    //ksp ("com.github.bumptech.glide:ksp:4.16.0")

    // for dimens
    implementation ("com.intuit.sdp:sdp-android:1.1.1")

    // Navigatiom
    implementation ("androidx.navigation:navigation-fragment-ktx:2.8.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.8.3")
    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.8.3")

    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.1.1")

    // Paging
    implementation ("androidx.paging:paging-runtime-ktx:3.3.2")

    // Firebase Modules
    implementation (platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation ("com.google.firebase:firebase-database-ktx")
}