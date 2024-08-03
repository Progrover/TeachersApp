plugins {
    id(Plugins.application)
    id(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
}

android {
    namespace = ProjectConfig.appBundle
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appBundle
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = ProjectConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures {
            buildConfig = true
        }
    }

    buildTypes {
        getByName(ProjectConfig.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(ProjectConfig.PROGUARD_DEFAULT),
                ProjectConfig.PROGUARD_FILE
            )
        }
        getByName(ProjectConfig.DEBUG) {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ""
            versionNameSuffix = "-DEV"
            signingConfig = signingConfigs.getByName(ProjectConfig.DEBUG)
        }
    }
    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName =
                    "Mentorium build ${variant.baseName} - ${variant.versionName} ${variant.versionCode}.apk"
                output.outputFileName = outputFileName
            }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    coreBase()
    coreTheme()
    coreUiCommon()
    base()
    navigation()
    compose()
    hilt()
    coil()
    work()
    coroutinesPlayServices()

    authApi()
    profileApi()
    chatApi()
    mainScreenApi()
    joblistApi()

    implementation(project(Feature.Auth.impl))
    implementation(project(Feature.Profile.impl))
    implementation(project(Feature.Chat.impl))
    implementation(project(Feature.MainScreen.impl))
    implementation(project(Feature.JobList.impl))
}

kapt {
    correctErrorTypes = true
}