plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace =libs.versions.appinfo.packagename.get()
    compileSdk = libs.versions.appinfo.compileSdk.get().toInt()

    defaultConfig {
        applicationId =  libs.versions.appinfo.packagename.get()
        minSdk = libs.versions.appinfo.minSdk.get().toInt()
        targetSdk= libs.versions.appinfo.targetSdk.get().toInt()
        versionCode = libs.versions.appinfo.versionCode.get().toInt()
        versionName = libs.versions.appinfo.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.toVersion(libs.versions.appinfo.jvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.appinfo.jvm.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.appinfo.jvm.get()
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.appinfo.jvm.get().toInt()))
    }
}

dependencies {
    implementation(libs.androidx.material3.android)
    coreLibraryDesugaring (libs.desugar.jdk.libs)
    implementation(fileTree("libs") { include("*.jar", "*.aar") })

    //arch : core/ui
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.material)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.browser)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)

    // arch : lifecycle
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate)
    implementation(libs.lifecycle.common.java8)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //arch : compose
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)


    //arch : navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic.features.fragment)


    // arch : hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    //testing
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(kotlin("test"))


    //networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.converter.scalars)
    implementation(libs.stetho)
    implementation(libs.stetho.okhttp3)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation (libs.okhttp.urlconnection)
    implementation(libs.jsoup)
    implementation(libs.glide)
    implementation(libs.kotlinx.serialization)

    //ksp(libs.com.github.bumptech.glide.compiler)


    //misc: timber
    implementation(libs.timber)

    //misc : tv
    implementation(libs.androidx.leanback)
    implementation(libs.androidx.tv.material)


    // Compose Previews
    debugImplementation(libs.androidx.compose.ui.tooling)



}