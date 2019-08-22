#ifndef PLAYER_AUDIOPLAYER_H
#define PLAYER_AUDIOPLAYER_H


#include <SuperpoweredAdvancedAudioPlayer.h>
#include <AndroidIO/SuperpoweredAndroidAudioIO.h>

#define log_print __android_log_print

#define SUPERPOWERED_TEMPORARY_LICENSE_KEY "ExampleLicenseKey-WillExpire-OnNextUpdate"

class AudioPlayer {
public:

    AudioPlayer(unsigned int sampleRate, unsigned int bufferSize);

    ~AudioPlayer();

    void playAudioFile(const char *path, int fileOffset, int fileLength);

    bool processAudio(short int *output, unsigned int numberOfSamples);

private:
    SuperpoweredAdvancedAudioPlayer *player;
    SuperpoweredAndroidAudioIO *audioIO;

    float *stereoBuffer;
};


#endif //PLAYER_AUDIOPLAYER_H