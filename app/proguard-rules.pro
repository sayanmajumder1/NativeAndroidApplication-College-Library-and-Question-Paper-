# Keep all classes and members in your application package
-keep class com.example.myapplication.** { *; }

# Keep public methods of Activity and Service classes
-keepclassmembers class * extends android.app.Activity {
    public *;
}
-keepclassmembers class * extends android.app.Service {
    public *;
}

# Keep resources and methods used by Activities
-keepclassmembers class * extends android.app.Activity {
    public void onCreate(android.os.Bundle);
}

# Keep classes and members annotated with @Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclassmembers @android.support.annotation.Keep class * {*;}

# Keep necessary attributes and annotations
-keepattributes Signature
-keepattributes *Annotation*

# Keep necessary classes and members from Firebase
-keep class com.google.firebase.** { *; }

# Keep necessary classes and members from Material Design components
-keep class com.google.android.material.** { *; }

# Keep necessary classes and members from androidx libraries
-keep class androidx.appcompat.** { *; }
-keep class androidx.constraintlayout.** { *; }
-keep class androidx.activity.** { *; }

# Keep necessary classes and members from Apache Commons IO
-keep class org.apache.commons.io.** { *; }

# Keep necessary classes and members from Glide
-keep class com.github.bumptech.glide.** { *; }

# Keep necessary classes and members from PDFViewPager library
-keep class es.voghdev.pdfviewpager.** { *; }

# Ignore warnings from specific libraries
-dontwarn com.google.firebase.**
-dontwarn com.google.android.material.**
-dontwarn androidx.appcompat.**
-dontwarn androidx.constraintlayout.**
-dontwarn androidx.activity.**
-dontwarn org.apache.commons.io.**
-dontwarn com.github.bumptech.glide.**
-dontwarn es.voghdev.pdfviewpager.**
