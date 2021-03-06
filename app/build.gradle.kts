plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 30

    defaultConfig {
        applicationId = "com.dancing_koala.primevideo"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
        kotlinCompilerVersion = "1.4.32"
    }
}

dependencies {

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha04")

    implementation("com.github.zsoltk:compose-router:0.24.2")


    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")

    implementation("com.google.android.material:material:1.3.0")

    val accompanistVersion = "0.8.1"

    implementation("com.google.accompanist:accompanist-swiperefresh:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-pager-indicators:$accompanistVersion")

    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.runtime:runtime:${rootProject.extra["compose_version"]}")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_version"]}")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0-alpha07")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}