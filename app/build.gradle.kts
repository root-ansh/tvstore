plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
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
    }
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.appinfo.jvm.get().toInt()))
    }
}

dependencies {
    coreLibraryDesugaring (libs.desugar.jdk.libs)
    implementation(fileTree("libs") { include("*.jar", "*.aar") })

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.material)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation(libs.kotlinx.serialization.json)

    //testing
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate)
    implementation(libs.lifecycle.common.java8)

    implementation(libs.browser)

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
    //ksp(libs.com.github.bumptech.glide.compiler)




    implementation( libs.exoplayer)

    //misc: timber
    implementation(libs.timber)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)



    implementation(libs.androidx.leanback)
}