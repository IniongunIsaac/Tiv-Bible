/**
 * Created by Isaac Iniongun on 2019-11-15.
 * For Tiv Bible project.
 */

object DepVersions {

    const val applicationId = "com.iniongun.tivbible"

    const val targetSdkVersion = 29
    const val compileSdkVersion = 29
    const val minSdkVersion = 16
    const val buildToolsVersion = "29.0.2"

    const val versionCode = 1
    const val versionName = "1.0"

    const val java = 1.8

    const val kotlin = "1.3.61"
    const val gradle = "3.5.3"
    const val navLifecycle = "2.1.0"
    const val appCompat = "1.1.0"
    const val androidXCoreKts = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val materialDesign = "1.1.0-alpha10"
    const val okhttp = "3.12.0"
    const val retrofit = "2.4.0"
    const val retrofitCoroutinesAdapter = "0.9.2"
    const val dagger = "2.25.3"
    const val coroutines = "1.3.3"
    const val gson = "2.8.6"
    const val legacySupport = "1.0.0"
    const val architectureCore = "2.1.0"

    const val javaxInject = "1"
    const val javaxAnnotation = "1.0"
    const val room = "2.2.3"
    const val rxJava = "2.2.16"
    const val rxAndroid = "2.1.1"

    const val threeTenAndroidBackport = "1.2.1"
    const val jacoco = "0.8.5"
    const val commonsLang3 = "3.9"
    const val commonsText = "1.8"

    const val googleServices = "4.3.3"
    const val firebaseAnalytics = "17.2.1"
    const val firebasePerformanceMonitoring = "19.0.4"
    const val firebasePerformanceMonitoringPlugin = "1.3.1"
    const val firebaseMessaging = "20.1.0"
    const val firebaseAppDistribution = "1.3.1"
    const val multiDex = "2.0.1"
    const val fabricGradle = "1.31.2"
    const val crashlytics = "2.10.1"

    const val juint = "4.12"
    const val junitExtension = "1.1.1"
    const val junit5DeMannodermausGradlePlugin = "1.5.1.0"
    const val junit5 = "5.5.2"
    const val mockK = "1.9.3"
    const val androidXTestCore = "1.2.0"
    const val espresso = "3.2.0"
    const val hamcrest = "1.3"
    const val robolectric = "4.3.1"
    const val threeTenBackport = "1.4.0"
}

object Modules {
    const val app = ":app"
    const val roomdb = ":roomdb"
    const val entities = ":entities"
    const val repository = ":repository"
    const val preferences = ":preferences"
    const val common = ":common"
}

object Dependencies {

    //Classpaths
    const val gradle = "com.android.tools.build:gradle:${DepVersions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${DepVersions.kotlin}"
    const val navSafeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${DepVersions.navLifecycle}"
    const val junit5DeMannodermausGradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:${DepVersions.junit5DeMannodermausGradlePlugin}"
    const val jacoco = "org.jacoco:org.jacoco.core:${DepVersions.jacoco}"
    const val googleServices = "com.google.gms:google-services:${DepVersions.googleServices}"
    const val fabricGradle = "io.fabric.tools:gradle:${DepVersions.fabricGradle}"
    const val firebasePerformanceMonitoringPlugin = "com.google.firebase:perf-plugin:${DepVersions.firebasePerformanceMonitoringPlugin}"
    const val firebaseAppDistribution = "com.google.firebase:firebase-appdistribution-gradle:${DepVersions.firebaseAppDistribution}"



    //Firebase
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:${DepVersions.firebaseAnalytics}"
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${DepVersions.crashlytics}"
    const val firebasePerformanceMonitoring = "com.google.firebase:firebase-perf:${DepVersions.firebasePerformanceMonitoring}"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging:${DepVersions.firebaseMessaging}"

    //Kotlin Standard Library
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${DepVersions.kotlin}"

    //AppCompat
    const val appCompat = "androidx.appcompat:appcompat:${DepVersions.appCompat}"

    //AndroidXCore
    const val androidXCoreKts = "androidx.core:core-ktx:${DepVersions.androidXCoreKts}"

    //Constraint Layout
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${DepVersions.constraintLayout}"

    //Material Design
    const val materialDesign = "com.google.android.material:material:${DepVersions.materialDesign}"

    //Navigation
    const val navFragmentKts = "androidx.navigation:navigation-fragment-ktx:${DepVersions.navLifecycle}"
    const val navUIKts = "androidx.navigation:navigation-ui-ktx:${DepVersions.navLifecycle}"

    //Gson
    const val gson = "com.google.code.gson:gson:${DepVersions.gson}"

    //Kotlin Coroutines
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${DepVersions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${DepVersions.coroutines}"

    //Okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:${DepVersions.okhttp}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${DepVersions.okhttp}"

    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${DepVersions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${DepVersions.retrofit}"
    const val retrofitCoroutinesAdapter =  "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${DepVersions.retrofitCoroutinesAdapter}"

    // ViewModel
    const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:${DepVersions.navLifecycle}"
    const val lifecycleViewModelKts = "androidx.lifecycle:lifecycle-viewmodel-ktx:${DepVersions.navLifecycle}"
    const val lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${DepVersions.navLifecycle}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${DepVersions.legacySupport}"

    //Dagger
    const val dagger = "com.google.dagger:dagger:${DepVersions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${DepVersions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${DepVersions.dagger}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${DepVersions.dagger}"

    //RxJava
    const val rxJava = "io.reactivex.rxjava2:rxjava:${DepVersions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${DepVersions.rxAndroid}"

    //Room
    const val room = "androidx.room:room-runtime:${DepVersions.room}"
    const val roomRx = "androidx.room:room-rxjava2:${DepVersions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${DepVersions.room}"
    const val roomTesting = "android.arch.persistence.room:testing:${DepVersions.room}"

    //Java Annotations
    const val javax = "javax.inject:javax.inject:${DepVersions.javaxInject}"
    const val javaxAnnotation = "javax.annotation:jsr250-api:${DepVersions.javaxAnnotation}"

    //ThreeTen Android Backport for DateTime
    const val threeTenAndroidBackport = "com.jakewharton.threetenabp:threetenabp:${DepVersions.threeTenAndroidBackport}"

    //Apache string utilities
    const val wordUtilities = "org.apache.commons:commons-lang3:${DepVersions.commonsLang3}"
    const val commonsText = "org.apache.commons:commons-text:${DepVersions.commonsText}"

    //Others
    const val multiDex = "androidx.multidex:multidex:${DepVersions.multiDex}"

    //Testing
    const val junit = "junit:junit:${DepVersions.juint}"
    const val androidXJunitExtension = "androidx.test.ext:junit:${DepVersions.junitExtension}"
    const val androidXArchCoreRuntime = "androidx.arch.core:core-runtime:${DepVersions.architectureCore}"
    const val androidXArchCoreTesting = "androidx.arch.core:core-testing:${DepVersions.architectureCore}"
    const val junit5JupiterApi = "org.junit.jupiter:junit-jupiter-api:${DepVersions.junit5}"
    const val junit5JupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${DepVersions.junit5}"
    const val junit5JupiterParams = "org.junit.jupiter:junit-jupiter-params:${DepVersions.junit5}"
    const val mockK = "io.mockk:mockk:${DepVersions.mockK}"
    const val androidXTestCore = "androidx.test:core:${DepVersions.androidXTestCore}"
    const val androidXTestCoreKtx = "androidx.test:core-ktx:${DepVersions.androidXTestCore}"
    const val androidXTestRunner = "androidx.test:runner:${DepVersions.androidXTestCore}"
    const val androidXTestRules = "androidx.test:rules:${DepVersions.androidXTestCore}"
    const val espresso = "androidx.test.espresso:espresso-core:${DepVersions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${DepVersions.espresso}"
    const val hamcrest = "org.hamcrest:hamcrest-all:${DepVersions.hamcrest}"
    const val roboelectric = "org.robolectric:robolectric:${DepVersions.robolectric}"
    const val threeTenBackport = "org.threeten:threetenbp:${DepVersions.threeTenBackport}"
}