-keep class com.bumptech.glide.** { *; }
-keep class * extends com.bumptech.glide.module.AppGlideModule { *; }

-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

-keep class dagger.hilt.** { *; }
-dontwarn dagger.hilt.**

-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

-keepattributes *Annotation*
-keep class * {
    public private *;
}

-keep interface * {
    public private *;
}

-dontwarn kotlin.**
-dontwarn androidx.**
