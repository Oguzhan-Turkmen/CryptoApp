plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    namespace = "com.example.cryptoapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.cryptoapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", "\"" + "${System.getenv("API_KEY_CRYPTO")}" + "\"")
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Pagination
    val pagingVersion = rootProject.extra["paging_version"]
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.paging:paging-compose:3.2.0")

    // Dagger Hilt
    val hiltVersion = rootProject.extra["hilt_version"]
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Lifecycle
    val lifecycleVersion = rootProject.extra["lifecycle_version"]
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")

    // Coil
    val coilVersion = rootProject.extra["coil_version"]
    implementation("io.coil-kt:coil:$coilVersion")
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Truth
    val truthVersion = rootProject.extra["coil_version"]
    testImplementation("com.google.truth:truth:$truthVersion")

    // Mockito
    val mockitoKotlinVersion = rootProject.extra["mockito_kotlin_version"]
    val mockitoInlineVersion = rootProject.extra["mockito_inline_version"]
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testImplementation("org.mockito:mockito-inline:$mockitoInlineVersion")

    // Coroutines-Test
    val coroutineVersion = rootProject.extra["coroutine_version"]
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion")

    // Lifecycle
    val lifeCycleVersion = rootProject.extra["lifecycle_version"]
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Retrofit & okhttp
    val retrofitVersion = rootProject.extra["retrofit_version"]
    val okHttpVersion = rootProject.extra["okhttp_version"]
    val retrofitInterceptorVersion = rootProject.extra["retrofit_interceptor_version"]
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$retrofitInterceptorVersion")

    //Navigation
    val navigatioVersion = rootProject.extra["nav_version"]
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.navigation:navigation-compose:$navigatioVersion")

    //Room
    val roomVersion = rootProject.extra["room_version"]
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    //Splash Screen
    val splashVersion = rootProject.extra["splash_version"]
    implementation("androidx.core:core-splashscreen:$splashVersion")

    //Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")

}