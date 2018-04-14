@file:JvmName("Keystore")

import org.gradle.api.Project

const val releaseStore = "../signing/release.keystore"
const val debugStore = "../signing/debug.keystore"

val Project.storePassword get() = properties["XLABS_STORE_PASSWORD"].toString()
val Project.keyAlias get() = properties["XLABS_KEY_ALIAS"].toString()
val Project.keyPassword get() = properties["XLABS_KEY_PASSWORD"].toString()
