apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
  compileSdkVersion Version.compile_sdk
  defaultConfig {
    applicationId "${packageName}"
    minSdkVersion Version.min_sdk
    targetSdkVersion Version.target_sdk
    versionCode 1
    versionName "1.0"
    resConfig "en"
    testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

    archivesBaseName = "$project.name-$versionName"
  }

  signingConfigs {
      release {
        storeFile file(Keystore.releaseStore)
        storePassword Keystore.getStorePassword(project)
        keyAlias Keystore.getKeyAlias(project)
        keyPassword Keystore.getKeyPassword(project)
      }

      debug {
        storeFile file(Keystore.debugStore)
      }
    }

  buildTypes {
    debug {
      applicationIdSuffix ".debug"
      signingConfig signingConfigs.debug
    }
    release {
      if (file(Keystore.releaseStore).exists()) {
        signingConfig signingConfigs.release
      } else {
        signingConfig signingConfigs.debug
      }
      postprocessing {
        proguardFiles 'proguard-rules.pro'
        removeUnusedResources = true
        removeUnusedCode = true
        optimizeCode = true
        obfuscate = true
      }
    }
  }
}

dependencies {
  implementation project(':lib-common')
  implementation Libs.appcompat_v7
  implementation Libs.constraint_layout
}