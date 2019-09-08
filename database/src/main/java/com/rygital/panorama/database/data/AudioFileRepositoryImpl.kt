package com.rygital.panorama.database.data

import com.rygital.core.data.AudioFileRepository
import com.rygital.core.model.AudioFile
import com.rygital.panorama.database.dao.AudioFileDao
import com.rygital.panorama.database.di.DatabaseScope
import javax.inject.Inject


@DatabaseScope
internal class AudioFileRepositoryImpl @Inject constructor(
        private val audioFileDao: AudioFileDao
) : AudioFileRepository {

    override suspend fun setAudioFiles(items: List<AudioFile>) {
        audioFileDao.deleteAllAndInsert(items.convertToEntities())
    }

    override suspend fun getAudioFiles(): List<AudioFile> =
            audioFileDao.getAll().convertToModels()
}