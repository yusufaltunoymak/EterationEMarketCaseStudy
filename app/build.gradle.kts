plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //Hilt
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    //Navigation
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.altunoymak.eterationemarketcasestudy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.altunoymak.eterationemarketcasestudy"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    //Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    //Retrofit
    implementation (libs.gson)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //Glide
    implementation (libs.glide)
    //Logging Interceptor
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)
    //Room
    implementation (libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    annotationProcessor (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)
    //ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.common.java8)
    implementation (libs.androidx.lifecycle.viewmodel.savedstate)
}
kapt {
    correctErrorTypes = true
}