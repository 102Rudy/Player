#include <SuperpoweredSimple.h>
#include <malloc.h>
#include <SLES/OpenSLES_AndroidConfiguration.h>
#include <SLES/OpenSLES.h>
#include <SuperpoweredCPU.h>
#include <android/log.h>
#include <Superpowered.h>
#include "AudioPlayer.h"
#include "Logger.h"

// This is called periodically by the audio engine.
static bool audioProcessing(
        void *clientData,               // custom pointer
        short int *audioIO,             // 16-bit stereo interleaved audio input and/or output
        int numberOfSamples,            // number of samples to process
        int __unused sampleRate         // current sample rate in Hz
) {
    return ((AudioPlayer *) clientData)->processAudio(audioIO, (unsigned int) numberOfSamples);
}

AudioPlayer::AudioPlayer(unsigned int sampleRate, unsigned int bufferSize) {
    SuperpoweredInitialize(
            SUPERPOWERED_TEMPORARY_LICENSE_KEY,
            false,                      // enableAudioAnalysis
            false,                      // enableFFTAndFrequencyDomain
            false,                      // enableAudioTimeStretching
            false,                      // enableAudioEffects
            true,                       // enableAudioPlayerAndDecoder
            false,                      // enableCryptographics
            false                       // enableNetworking
    );

    stereoBuffer = (float *) malloc(sizeof(float) * 2 * bufferSize);

    player = new SuperpoweredAdvancedAudioPlayer(
            nullptr,                    // client data
            nullptr,                    // callback function
            sampleRate,                 // sampling rate
            0                           // cached point count
    );

    audioIO = new SuperpoweredAndroidAudioIO(
            sampleRate,                 // sampling rate
            bufferSize,                 // buffer size
            false,                      // enableInput
            true,                       // enableOutput
            audioProcessing,            // process callback function
            this,                       // clientData
            -1,                         // inputStreamType (-1 = default)
            SL_ANDROID_STREAM_MEDIA     // outputStreamType (-1 = default)
    );
}

AudioPlayer::~AudioPlayer() {
    delete player;
    delete audioIO;

    free(stereoBuffer);
}

void AudioPlayer::playAudioFile(const char *path, int fileOffset, int fileLength) {
    Logger::instance()->v("AudioPlayer::playAudioFile", "path to file: %s", path);

    player->open(path, fileOffset, fileLength);
    player->play(false);

    SuperpoweredCPU::setSustainedPerformanceMode(player->playing);  // prevent dropouts
}

bool AudioPlayer::processAudio(short int *output, unsigned int numberOfSamples) {
    if (player->process(stereoBuffer, false, numberOfSamples)) {
        SuperpoweredFloatToShortInt(stereoBuffer, output, numberOfSamples);
        return true;
    } else {
        return false;
    }
}

void AudioPlayer::onBackground() {
    Logger::instance()->v("AudioPlayer::onBackground", "on background");
    audioIO->onBackground();
}

void AudioPlayer::onForeground() {
    Logger::instance()->v("AudioPlayer::onForeground", "on foreground");
    audioIO->onForeground();
}