plugins {
    id(Plugins.library)
    id(Plugins.android)
    id(Plugins.hilt)
    kotlin(Plugins.kapt)
}

android {
    namespace = Core.base.toBundle()
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

    restApiMonitoring()
    hilt()
    viewModel()
    coroutines()
    navigation()
    retrofit()
    timber()
    coil()
    work()
    appwrite()
    securityCrypto()
}