package com.rygital.player.explorer.data

import com.rygital.core.data.AudioFileRepository
import com.rygital.core.model.AudioFile
import com.rygital.player.explorer.domain.ExplorerRepository
import javax.inject.Inject

internal class ExplorerRepositoryImpl @Inject constructor(
        private val fileManager: FileManager,
        private val audioFileRepository: AudioFileRepository
) : ExplorerRepository {

    override suspend fun loadFilesFromStorage(): List<AudioFile> {
        val items = fileManager.getAllAudioFilesFromStorage()
        audioFileRepository.setAudioFiles(items)
        return audioFileRepository.getAudioFiles()
    }

    override suspend fun loadFilesFromDatabase(): List<AudioFile> =
            audioFileRepository.getAudioFiles()
}