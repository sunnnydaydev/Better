plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    dataBinding{
        enable = true
    }
}


task("copyCommonNavigationGraph"){
        println("copyCommonNavigationGraph:start")
        // todo 实现nav file copy 建议使用Python做
        println("copyCommonNavigationGraph:end")
}

dependencies {
    // group lib
    defaultLib()
    ktxLib()
    navigationLib()
    hiltLib()
    testLib()

    // single lib
    implementation (Dependency.timber)
    implementation(Dependency.reclaim)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
