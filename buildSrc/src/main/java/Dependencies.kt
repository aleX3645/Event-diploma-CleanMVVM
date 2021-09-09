
object Versions {
    const val jvmTarget = "1.8"
    const val gradleVersion = "4.1.1"

    const val kotlinX = "1.6.0"
    const val appcompat = "1.3.1"
    const val materials = "1.4.0"
    const val navigation = "2.3.5"

    const val junit = "4.13.2"
    const val junitX = "1.1.3"
    const val espresso = "3.3.0"
}

object Deps {
    val app_name = "com.alex3645.eventdiploma_mvvm"

    const val gradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
    const val kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${AndroidConfig.kotlin}"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${AndroidConfig.kotlin}"

    val kotlin_x = "androidx.core:core-ktx:${Versions.kotlinX}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val materials = "com.google.android.material:material:${Versions.materials}"

    val android_x_test = "androidx.test.runner.AndroidJUnitRunner"
    val junit = "junit:junit:${Versions.junit}"
    val android_x_junit = "androidx.test.ext:junit:${Versions.junitX}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val navDynamicFeatures = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
    val navNavigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}