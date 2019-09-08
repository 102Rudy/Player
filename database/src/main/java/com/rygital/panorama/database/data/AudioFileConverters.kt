package com.rygital.panorama.database.data

import com.rygital.core.model.AudioFile
import com.rygital.panorama.database.entity.AudioFileEntity

internal fun List<AudioFile>.convertToEntities(): List<AudioFileEntity> {
    val entities = mutableListOf<AudioFileEntity>()

    forEach {
        entities.add(it.convertToEntity())
    }

    return entities
}

internal fun AudioFile.convertToEntity(): AudioFileEntity = AudioFileEntity(0, title, pathToFile)

internal fun List<AudioFileEntity>.convertToModels(): List<AudioFile> {
    val entities = mutableListOf<AudioFile>()

    forEach {
        entities.add(it.convertToModel())
    }

    return entities
}

internal fun AudioFileEntity.convertToModel(): AudioFile = AudioFile(title, path)