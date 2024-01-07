plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.better"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.better"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    dataBinding{
        enable = true
    }
}

dependencies {
    implementation (Dependency.coreKtx)
    implementation (Dependency.appcompat)
    implementation (Dependency.material)
    implementation (Dependency.constraintlayout)
    testImplementation (Test.junit)
    androidTestImplementation (Test.ext)
    androidTestImplementation (Test.espressoCore)
    implementation (Dependency.lifeCycleKtx)
    implementation (Dependency.viewmodelKtx)
    implementation (Dependency.activityKtx)
    implementation (Dependency.timber)
}