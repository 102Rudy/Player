
#include <jni.h>
#include <string>
#include "AudioPlayer.h"
#include "Logger.h"

static constexpr int STATUS_OK = 0;
static constexpr int STATUS_NEED_DETACH = 1;
static constexpr int STATUS_ERROR = 2;

static AudioPlayer *audioPlayer = nullptr;

static JavaVM *javaVm;
static jobject audioFileEndListener;
static jmethodID onAudioFileEndMethodId;

static int getJNIEnvironment(JNIEnv **env) {
    int status = javaVm->GetEnv(reinterpret_cast<void **>(env), JNI_VERSION_1_6);

    if (status == JNI_OK) {
        return STATUS_OK;
    } else if (status == JNI_EDETACHED) {
        if (javaVm->AttachCurrentThread(env, nullptr) != 0) {
            return STATUS_ERROR;
        }

        return STATUS_NEED_DETACH;
    }

    return STATUS_ERROR;
}


extern "C" {

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void __unused *reserved) {
    Logger::instance()->v(__FUNCTION__, "onLoad audio lib");
    javaVm = vm;

    JNIEnv *env;

    if (vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    return JNI_VERSION_1_6;
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_initialize(
        JNIEnv __unused *env,
        jobject __unused pThis,
        jint sampleRate,
        jint bufferSize
) {
    audioPlayer = new AudioPlayer((unsigned int) sampleRate, (unsigned int) bufferSize);
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_setAudioFileEndCallback(
        JNIEnv *env,
        jobject __unused pThis,
        jobject listener
) {
    audioFileEndListener = env->NewGlobalRef(listener);

    jclass clazz = env->GetObjectClass(audioFileEndListener);
    onAudioFileEndMethodId = env->GetMethodID(clazz, "onAudioFileEnd", "()V");

    audioPlayer->setAudioFileEndCallback([] {
        JNIEnv *jniEnv;
        int status = getJNIEnvironment(&jniEnv);

        if (status == STATUS_ERROR) {
            Logger::instance()->e("setAudioFileEndCallback lambda", "error while getJNIEnvironment");
            return;
        }

        Logger::instance()->v("setAudioFileEndCallback lambda", "call onAudioFileEnd method");
        jniEnv->CallVoidMethod(audioFileEndListener, onAudioFileEndMethodId);

        if (status == STATUS_NEED_DETACH) {
            Logger::instance()->v("setAudioFileEndCallback lambda", "detach current thread");
            javaVm->DetachCurrentThread();
        }
    });
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_onBackground(
        JNIEnv __unused *env,
        jobject __unused pThis
) {
    audioPlayer->onBackground();
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_onForeground(
        JNIEnv __unused *env,
        jobject __unused pThis
) {
    audioPlayer->onForeground();
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_openAudioFile(
        JNIEnv *env,
        jobject __unused pThis,
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
        jobject __unused pThis
) {
    audioPlayer->play();
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_pause(
        JNIEnv __unused *env,
        jobject __unused pThis
) {
    audioPlayer->pause();
}

JNIEXPORT void
Java_com_rygital_audiolibrary_AudioLibWrapper_seekTo(
        JNIEnv __unused *env,
        jobject __unused pThis,
        jdouble positionPercent
) {
    audioPlayer->seekTo(positionPercent);
}


}