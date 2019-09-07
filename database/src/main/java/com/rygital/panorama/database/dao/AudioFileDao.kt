package com.rygital.panorama.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.rygital.panorama.database.entity.AudioFileEntity


@Dao
internal interface AudioFileDao : BaseDao<AudioFileEntity> {

    @Query("SELECT * FROM audioFileEntity")
    fun getAll(): List<AudioFileEntity>

}