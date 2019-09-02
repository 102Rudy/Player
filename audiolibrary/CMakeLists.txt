cmake_minimum_required(VERSION 3.6)

add_definitions(-DSUPERPOWERED_LICENSE_KEY=${SUPERPOWERED_LICENSE_KEY})

add_library(
        audio-lib
        SHARED
        src/main/cpp/audiolib.cpp
        src/main/cpp/AudioPlayer.cpp
        src/main/cpp/Logger.cpp
        ${PATH_TO_SUPERPOWERED}/AndroidIO/SuperpoweredAndroidAudioIO.cpp
)

target_link_libraries(
        audio-lib
        log
        android
        OpenSLES
        ${PATH_TO_SUPERPOWERED}/libSuperpoweredAndroid${ANDROID_ABI}.a
)