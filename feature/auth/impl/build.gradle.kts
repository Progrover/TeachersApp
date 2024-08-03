plugins {
    id(Plugins.library)
    id(Plugins.android)
    id(Plugins.hilt)
    kotlin(Plugins.kapt)
}

android {
    namespace = Feature.Auth.impl.toBundle()
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk

        testInstrumentationRunner = ProjectConfig.TEST_INSTRUMENTATION_RUNNER
    }

    compileOptions {
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
}

dependencies {

    coreBase()
    coreTheme()
    coreUiCommon()

    authApi()
    mainScreenApi()
    appwrite()

    hilt()
    compose()
    navigation()
    coil()

    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //encrypt
    implementation("org.mindrot:jbcrypt:0.4")
}

kapt {
    correctErrorTypes = true
}