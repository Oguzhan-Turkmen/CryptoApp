buildscript {
    extra.apply {
        set("hilt_version", "2.47")
        set("lifecycle_version", "2.5.1")
        set("coil_version", "2.2.0")
        set("mockito_kotlin_version", "4.0.0")
        set("mockito_inline_version", "3.0.0")
        set("coroutine_version", "1.3.9")
        set("retrofit_version", "2.9.0")
        set("retrofit_interceptor_version", "4.9.3")
        set("okhttp_version", "4.11.0")
        set("paging_version", "3.1.1")
        set("nav_version", "2.5.2")
        set("room_version", "2.5.0")
        set("splash_version", "1.0.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id ("com.google.dagger.hilt.android") version "2.47" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
}