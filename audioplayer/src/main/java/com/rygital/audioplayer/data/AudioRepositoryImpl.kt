package com.rygital.audioplayer.data

import com.rygital.audioplayer.domain.AudioRepository
import com.rygital.audioplayer.domain.Song

internal class AudioRepositoryImpl : AudioRepository {

    override fun getCurrentPlaylist(): List<Song> {
        return listOf()
    }
}