#include <SuperpoweredSimple.h>
#include <malloc.h>
#include <SLES/OpenSLES_AndroidConfiguration.h>
#include <SLES/OpenSLES.h>
#include <SuperpoweredCPU.h>
#include <Superpowered.h>
#include <utility>
#include <functional>
#include "AudioPlayer.h"
#include "Logger.h"

// Called by the player
static void playerEvent(
        void *clientData,                               // custom pointer to client data
        SuperpoweredAdvancedAudioPlayerEvent event,     // What happened
        void *value                                     // A pointer to a stemsInfo structure or NULL for LoadSuccess
) {
    ((AudioPlayer *) clientData)->playerEventCallback(event, value);
}

// This is called periodically by the audio engine.
static bool audioProcessing(
        void *clientData,               // custom pointer to client data
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
            this,                       // client data
            playerEvent,                // callback function
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

void AudioPlayer::setAudioFileEndCallback(std::function<void()> audioFileEndCallback) {
    this->audioFileEndCallback = std::move(audioFileEndCallback);
}

void AudioPlayer::onBackground() {
    audioIO->onBackground();
}

void AudioPlayer::onForeground() {
    audioIO->onForeground();
}

void AudioPlayer::playerEventCallback(SuperpoweredAdvancedAudioPlayerEvent event, void *value) {
    switch (event) {
        case SuperpoweredAdvancedAudioPlayerEvent_LoadSuccess:
            break;
        case SuperpoweredAdvancedAudioPlayerEvent_LoadError:
            Logger::instance()->e(__FUNCTION__, "load error: %s", (char *) value);
            break;
        case SuperpoweredAdvancedAudioPlayerEvent_EOF:
            Logger::instance()->v(__FUNCTION__, "end of file");
            if (audioFileEndCallback != nullptr) {
                Logger::instance()->v(__FUNCTION__, "end of file, call callback");
                audioFileEndCallback();
            }
            break;
        default:
            break;
    }
}

bool AudioPlayer::processAudio(short int *output, unsigned int numberOfSamples) {
    if (player->process(stereoBuffer, false, numberOfSamples)) {
        SuperpoweredFloatToShortInt(stereoBuffer, output, numberOfSamples);
        return true;
    } else {
        return false;
    }
}

void AudioPlayer::openAudioFile(const char *path, int fileOffset, int fileLength) {
    Logger::instance()->v("AudioPlayer::openAudioFile", "path to file: %s", path);
    player->open(path, fileOffset, fileLength);
}

void AudioPlayer::play() {
    player->play(false);
    SuperpoweredCPU::setSustainedPerformanceMode(player->playing);  // prevent dropouts
}

void AudioPlayer::pause() {
    player->pause();
}

void AudioPlayer::seekTo(double positionPercent) {
    player->seek(positionPercent);
}
