
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
Java_com_rygital_audiolibrary_AudioLibWrapper_onBackground(
        JNIEnv __unused *env,
        jobject __unused obj
) {
    audioPlayer->onBackground();
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_onForeground(
        JNIEnv __unused *env,
        jobject __unused obj
) {
    audioPlayer->onForeground();
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_openAudioFile(
        JNIEnv *env,
        jobject __unused obj,
        jstring pathToFile,
        jint fileOffset,
        jint fileLength
) {
    const char *path = env->GetStringUTFChars(pathToFile, nullptr);
    audioPlayer->openAudioFile(path, fileOffset, fileLength);
    env->ReleaseStringUTFChars(pathToFile, path);
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_play(
        JNIEnv __unused *env,
        jobject __unused obj
) {
    audioPlayer->play();
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_pause(
        JNIEnv __unused *env,
        jobject __unused obj
) {
    audioPlayer->pause();
}


}