plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22") //kotlin version
    implementation("com.android.tools.build:gradle:8.3.1") //gradle plugin version
    //java poet необходима для сборки приложения с зависимостью hilt. без этой зависимости проект собрать невозможно
    implementation("com.squareup:javapoet:1.13.0")
}