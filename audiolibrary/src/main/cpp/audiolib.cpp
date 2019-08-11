
#include <jni.h>
#include <string>
#include <android/log.h>

extern "C" {

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void __unused *reserved) {
    __android_log_print(ANDROID_LOG_INFO, __FUNCTION__, "onLoad audio lib");
    JNIEnv *env;

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    return JNI_VERSION_1_6;
}

JNIEXPORT jstring JNICALL
Java_com_rygital_audiolibrary_AudioLibWrapper_stringFromJNI(
        JNIEnv *env,
        jobject /* this */
) {

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

}