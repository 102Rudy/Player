#include <android/log.h>
#include "Logger.h"

void Logger::v(const char *tag, const char *fmt, ...) {
    va_list args;
    va_start(args, fmt);
    __android_log_vprint(ANDROID_LOG_VERBOSE, tag, fmt, args);
    va_end(args);
}

void Logger::d(const char *tag, const char *fmt, ...) {
    va_list args;
    va_start(args, fmt);
    __android_log_vprint(ANDROID_LOG_DEBUG, tag, fmt, args);
    va_end(args);
}

void Logger::i(const char *tag, const char *fmt, ...) {
    va_list args;
    va_start(args, fmt);
    __android_log_vprint(ANDROID_LOG_INFO, tag, fmt, args);
    va_end(args);
}

void Logger::w(const char *tag, const char *fmt, ...) {
    va_list args;
    va_start(args, fmt);
    __android_log_vprint(ANDROID_LOG_WARN, tag, fmt, args);
    va_end(args);
}

void Logger::e(const char *tag, const char *fmt, ...) {
    va_list args;
    va_start(args, fmt);
    __android_log_vprint(ANDROID_LOG_ERROR, tag, fmt, args);
    va_end(args);
}