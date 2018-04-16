object Libs {
  // Testing
  const val assert_j = "com.squareup.assertj:assertj-android:1.2.0"
  const val junit = "junit:junit:4.12"
  const val test_runner = "com.android.support.test:runner:1.0.1"
  const val esspresso_core = "com.android.support.test.espresso:espresso-core:${Version.espresso}"

  // Android
  const val appcompat_v7 = "com.android.support:appcompat-v7:${Version.android_support}"
  const val recyclerview = "com.android.support:recyclerview-v7:${Version.android_support}"
  const val design = "com.android.support:design:${Version.android_support}"
  const val constraint_layout = "com.android.support.constraint:constraint-layout:1.1.0"
  const val android_ktx = "androidx.core:core-ktx:0.3"

  // Arch
  const val arch_room_runtime = "android.arch.persistence.room:runtime:${Version.arch_persistence}"
  const val arch_room_compiler = "android.arch.persistence.room:compiler:${Version.arch_persistence}"
  const val arch_lifecycle_ext = "android.arch.lifecycle:extensions:${Version.arch_lifecycle}"

  // Kotlin
  const val kotlin_jre7 = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Version.kotlin}"
  const val coroutine_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
  const val coroutine_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"

  // Misc
  const val timber = "com.jakewharton.timber:timber:4.7.0"
}