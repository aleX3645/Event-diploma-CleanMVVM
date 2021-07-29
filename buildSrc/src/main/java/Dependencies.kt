
object Versions {
    val kotlin = "1.4.32"

    val compileSdkVersion = 30
    val buildToolsVersion = "30.0.3"
    val minSdkVersion = 21
    val targetSdkVersion = 30
    val versionCode = 1
    val versionName = "1.0"

    val jvmTarget = "1.8"
    val gradleVersion = "4.1.1"

    val kotlinX = "1.6.0"
    val appcompat = "1.3.1"
    val materials = "1.4.0"

    val junit = "4.13.2"
    val junitX = "1.1.3"
    val espresso = "3.4.0"
}

object Deps {
    val app_name = "com.alex3645.eventdiploma_mvvm"

    val gradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
    val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    val kotlin_x = "androidx.core:core-ktx:${Versions.kotlinX}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val materials = "com.google.android.material:material:${Versions.materials}"

    val android_x_test = "androidx.test.runner.AndroidJUnitRunner"
    val junit = "junit:junit:${Versions.junit}"
    val android_x_junit = "androidx.test.ext:junit:${Versions.junitX}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}