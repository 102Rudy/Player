
#include <jni.h>
#include <string>
#include <android/log.h>
#include "AudioPlayer.h"

static AudioPlayer *audioPlayer = nullptr;

extern "C" {

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void __unused *reserved) {
    __android_log_print(ANDROID_LOG_INFO, __FUNCTION__, "onLoad audio lib");
    JNIEnv *env;

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    return JNI_VERSION_1_6;
}


JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_initialize(
        JNIEnv __unused *env,
        jobject __unused obj,
        jint sampleRate,
        jint bufferSize
) {
    audioPlayer = new AudioPlayer((unsigned int) sampleRate, (unsigned int) bufferSize);
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_playAudioFile(
        JNIEnv *env,
        jobject __unused obj,
        jstring pathToFile,
        jint fileOffset,
        jint fileLength
) {
    const char *path = env->GetStringUTFChars(pathToFile, nullptr);
    audioPlayer->playAudioFile(path, fileOffset, fileLength);
    env->ReleaseStringUTFChars(pathToFile, path);
}

}