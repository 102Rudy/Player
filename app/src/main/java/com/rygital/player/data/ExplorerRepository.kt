package com.rygital.player.data

import com.rygital.player.domain.AudioFile
import com.rygital.player.domain.ExplorerRepository
import javax.inject.Inject

class ExplorerRepositoryImpl @Inject constructor(
        private val fileManager: FileManager
) : ExplorerRepository {

    override fun loadFilesFromStorage(): List<AudioFile> {
        val audioFiles = fileManager.getAllAudioFilesFromStorage()

        // put audio files to database

         return audioFiles
    }

    override fun loadFilesFromDatabase() {

    }
}