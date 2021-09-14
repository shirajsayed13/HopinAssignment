#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring
Java_com_shiraj_network_NetString_baseUrlFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string baseUrl = "https://api.github.com/";
    return env->NewStringUTF(baseUrl.c_str());
}