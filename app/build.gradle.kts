plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("D:\\School\\SoulmatchAlpha\\soulmatchkeystore.jks")
            storePassword = "soulmatchapp"
            keyAlias = "soulmatchkeystore"
            keyPassword = "soulmatchapp"
        }
    }
    namespace = "com.fardan.soulmatchalpha"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.fardan.soulmatchalpha"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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

    buildFeatures {
        viewBinding= true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.google.firebase:firebase-auth-ktx:21.3.0")
    implementation ("com.google.firebase:firebase-bom:31.5.0")
    implementation("com.google.android.gms:play-services-auth:20.4.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(kotlin("script-runtime"))
}