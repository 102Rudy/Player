package com.rygital.player.explorer.data

import com.rygital.core.model.AudioFile
import com.rygital.player.explorer.domain.ExplorerRepository
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