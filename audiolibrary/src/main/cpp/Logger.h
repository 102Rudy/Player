#ifndef PLAYER_LOGGER_H
#define PLAYER_LOGGER_H

#include <stdarg.h>

#if defined (__GNUC__)
#define FORMAT(fmt, arg) __attribute__((__format__(printf, fmt, arg)))
#else
#define FORMAT(fmt, arg)
#endif

class Logger {
public:

    static Logger *instance() {
        static Logger instance;
        return &instance;
    }

    Logger(Logger const &) = delete;

    void operator=(Logger const &) = delete;

    void v(const char *tag, const char *fmt, ...) FORMAT(3, 4);

    void d(const char *tag, const char *fmt, ...) FORMAT(3, 4);

    void i(const char *tag, const char *fmt, ...) FORMAT(3, 4);

    void w(const char *tag, const char *fmt, ...) FORMAT(3, 4);

    void e(const char *tag, const char *fmt, ...) FORMAT(3, 4);

private:
    Logger() = default;
};

#endif //PLAYER_LOGGER_H