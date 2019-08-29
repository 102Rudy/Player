package com.rygital.player.explorer.data

import com.rygital.core.model.AudioFile
import com.rygital.player.explorer.domain.ExplorerRepository
import javax.inject.Inject

internal class ExplorerRepositoryImpl @Inject constructor(
        private val fileManager: FileManager
) : ExplorerRepository {

    override fun loadFilesFromStorage(): List<AudioFile> {
        return fileManager.getAllAudioFilesFromStorage()
    }

    override fun loadFilesFromDatabase() {

    }
}