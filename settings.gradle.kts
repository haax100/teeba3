pluginManagement {
    repositories {
        google() // يفضل وضعه في الأعلى للوصول إلى مكتبات Google أولاً
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT) // تعديل وضع التفضيل
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Teeba"
include(":app")
