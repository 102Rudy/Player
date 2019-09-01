#ifndef PLAYER_AUDIOPLAYER_H
#define PLAYER_AUDIOPLAYER_H


#include <SuperpoweredAdvancedAudioPlayer.h>
#include <AndroidIO/SuperpoweredAndroidAudioIO.h>
#include <functional>

#define SUPERPOWERED_TEMPORARY_LICENSE_KEY "ExampleLicenseKey-WillExpire-OnNextUpdate"

class AudioPlayer {
public:

    AudioPlayer(unsigned int sampleRate, unsigned int bufferSize);

    ~AudioPlayer();

    void setAudioFileEndCallback(std::function<void()> audioFileEndCallback);

    void onBackground();

    void onForeground();

    void playerEventCallback(SuperpoweredAdvancedAudioPlayerEvent event, void *value);

    bool processAudio(short int *output, unsigned int numberOfSamples);

    void openAudioFile(const char *path, int fileOffset, int fileLength);

    void play();

    void pause();

private:
    SuperpoweredAdvancedAudioPlayer *player;
    SuperpoweredAndroidAudioIO *audioIO;

    float *stereoBuffer;

    std::function<void()> audioFileEndCallback;
};


#endif //PLAYER_AUDIOPLAYER_H