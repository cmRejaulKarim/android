plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // Kotlin support
    id("org.jetbrains.kotlin.plugin.compose") // Compose plugin
    id("com.google.gms.google-services") // Firebase
}

android {
    namespace = "me.iamcrk.medinfo"
    compileSdk = 36

    defaultConfig {
        applicationId = "me.iamcrk.medinfo"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true  // <--- Enable Compose here!
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"  // Make sure this matches your Compose version
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))
    implementation("com.google.firebase:firebase-firestore")

    // Kotlin extensions
    implementation("androidx.core:core-ktx:1.12.0")

    // Compose dependencies
    implementation(platform("androidx.compose:compose-bom:2023.09.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui-tooling-preview")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
