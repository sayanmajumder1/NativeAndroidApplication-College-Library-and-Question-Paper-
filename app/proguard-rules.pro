# ================================
# CORE APP RULES
# ================================

# Keep all classes and members in your application package
-keep class com.example.myapplication.** { *; }

# Keep public methods of Activity and Service classes
-keepclassmembers class * extends android.app.Activity { public *; }
-keepclassmembers class * extends android.app.Service { public *; }

# Keep lifecycle methods of Activities
-keepclassmembers class * extends android.app.Activity {
    public void onCreate(android.os.Bundle);
}

# Keep classes and members annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }
-keepclassmembers @androidx.annotation.Keep class * { *; }

# Keep attributes and annotations
-keepattributes Signature
-keepattributes *Annotation*

# Hide source file and method names (Obfuscation)
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Optimize classes
-optimizationpasses 5
-allowaccessmodification

# ================================
# FIREBASE (AUTH, DATABASE, FIRESTORE, MESSAGING, CRASHLYTICS)
# ================================

-keep class com.google.firebase.** { *; }
-keep class com.google.firebase.auth.** { *; }
-keep class com.google.firebase.database.** { *; }
-keep class com.google.firebase.firestore.** { *; }
-keep class com.google.firebase.messaging.** { *; }
-keep class com.google.firebase.crashlytics.** { *; }

# ================================
# GSON (JSON PARSING)
# ================================

-keep class com.google.gson.** { *; }
-keep class com.google.code.gson.** { *; }

# Keep fields annotated with @SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ================================
# RETROFIT & OKHTTP (API CALLS)
# ================================

-keep class retrofit2.** { *; }
-keepclassmembers class * { @retrofit2.http.* <methods>; }

-keep class okhttp3.** { *; }
-keep class okhttp3.logging.HttpLoggingInterceptor { *; }
-keep class com.squareup.okhttp3.** { *; }

# ================================
# GLIDE (IMAGE LOADING)
# ================================

-keep class com.bumptech.glide.** { *; }
-keep class * extends com.bumptech.glide.module.AppGlideModule { *; }

# ================================
# ANDROIDX COMPONENTS
# ================================

-keep class androidx.appcompat.** { *; }
-keep class androidx.constraintlayout.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.fragment.app.** { *; }
-keep class androidx.navigation.** { *; }

# ================================
# GOOGLE PLAY SERVICES (AUTH)
# ================================

-keep class com.google.android.gms.auth.** { *; }
-keep class com.google.android.gms.tasks.** { *; }

# ================================
# MATERIAL COMPONENTS
# ================================

-keep class com.google.android.material.** { *; }

# ================================
# TESTING (JUnit & Espresso)
# ================================

-keep class org.junit.** { *; }
-keep class androidx.test.espresso.** { *; }

# ================================
# PDF VIEWERS (VoghDev & Bartek)
# ================================

-keep class es.voghdev.pdfviewpager.library.** { *; }

# Uncomment if using Bartek PDF Viewer
# -keep class com.github.barteksc.pdfviewer.** { *; }

# ================================
# IGNORE WARNINGS FROM SPECIFIC LIBRARIES
# ================================

-dontwarn com.google.firebase.**
-dontwarn com.google.android.material.**
-dontwarn androidx.appcompat.**
-dontwarn androidx.constraintlayout.**
-dontwarn androidx.activity.**
-dontwarn org.apache.commons.io.**
-dontwarn com.github.bumptech.glide.**
-dontwarn es.voghdev.pdfviewpager.**


