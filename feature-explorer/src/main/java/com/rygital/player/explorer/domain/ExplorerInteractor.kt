package com.rygital.player.explorer.domain

import com.rygital.core.model.AudioFile
import javax.inject.Inject

internal interface ExplorerRepository {
    suspend fun loadFilesFromStorage(): List<AudioFile>
    suspend fun loadFilesFromDatabase(): List<AudioFile>
}

interface ExplorerInteractor {
    suspend fun getSongs(): List<AudioFile>
    suspend fun refreshSongs(): List<AudioFile>
}

internal class ExplorerInteractorImpl @Inject constructor(
        private val repository: ExplorerRepository
) : ExplorerInteractor {

    override suspend fun getSongs(): List<AudioFile> =
            repository.loadFilesFromDatabase()

    override suspend fun refreshSongs(): List<AudioFile> =
            repository.loadFilesFromStorage()
}