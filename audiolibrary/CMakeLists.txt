cmake_minimum_required(VERSION 3.6)

add_library(
        audio-lib
        SHARED
        src/main/cpp/audiolib.cpp)

find_library(
        log-lib
        log
)

target_link_libraries(
        audio-lib
        ${log-lib}
)