# Add project specific ProGuard / R8 rules here.

-dontobfuscate
-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn com.google.common.**
-dontwarn javax.annotation.**

-keepattributes Signature,InnerClasses,EnclosingMethod,*Annotation*

# Preserve native methods and JNI callback classes
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class com.vectras.vm.utils.CpuHelper { *; }
-keep class com.vectras.vm.utils.GpuHelper { *; }
-keep class com.termux.app.TermuxInstaller { *; }
-keep class com.vectras.vm.x11.CmdEntryPoint { *; }
-keep class com.vectras.vm.x11.LorieView { *; }
-keep class com.vectras.vm.x11.Prefs { *; }
-keep class com.vectras.vm.x11.LoriePreferences** { *; }
-keep class * extends com.vectras.vm.x11.LoriePreferences$PrefsProto { *; }

# Settings and preference fragments
-keep class com.vectras.qemu.MainSettingsManager** { *; }
-keep class android.media.LoudnessCodecController { *; }

# Termux and VNC
-keep class com.termux.** { *; }
-keep class android.androidVNC.** { *; }

# Models, JSON and Gson serialization
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.google.gson.** { *; }
-keep class com.vectras.vm.main.vms.** { *; }
-keep class com.vectras.vm.main.romstore.** { *; }
-keep class com.vectras.vm.main.softwarestore.** { *; }
-keep class com.anbui.elephant.** { *; }
