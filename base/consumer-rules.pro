-keep class com.base.** {*;}
#------------------------------------------------Retrofit------------------------------------------#
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
#------------------------------------------------Retrofit------------------------------------------#


# -------------------------------------------------OkHttp3--------------------------------------------#
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# Retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn sun.misc.**
-dontwarn sorg.codehaus.mojo.animal_sniffer.**
-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**
# -------------------------------------------------OkHttp3--------------------------------------------#



#--------------------------------------------------Rxjava RxAndroid------------------------------------#
-dontwarn java.util.concurrent.Flow*
#--------------------------------------------------Rxjava RxAndroid--------------------------------#


#--------------------------------------------------Gson--------------------------------------------#
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class com.google.gson.stream.** { *; }
-keep class com.jyy.common.logic.gson.** { *; }

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}
#--------------------------------------------------Gson---------------------------------------------#