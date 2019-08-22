package com.rygital.player.explorer.domain

import com.rygital.core.model.AudioFile
import javax.inject.Inject

interface ExplorerRepository {
    fun loadFilesFromStorage(): List<AudioFile>
    fun loadFilesFromDatabase()
}

interface ExplorerInteractor {
    fun getSongs(): List<AudioFile>
    fun refreshSongs(): List<AudioFile>
}

class ExplorerInteractorImpl @Inject constructor(
        private val repository: ExplorerRepository
) : ExplorerInteractor {

    override fun getSongs(): List<AudioFile> {
        return repository.loadFilesFromStorage()
    }

    override fun refreshSongs(): List<AudioFile> {
        return repository.loadFilesFromStorage()
    }
}