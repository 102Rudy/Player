package com.rygital.panorama.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.rygital.panorama.database.entity.AudioFileEntity


@Dao
internal abstract class AudioFileDao : BaseDao<AudioFileEntity> {

    @Query("SELECT * FROM audioFileEntity")
    abstract suspend fun getAll(): List<AudioFileEntity>

    @Query("DELETE FROM audioFileEntity")
    abstract suspend fun deleteAll()

    @Transaction
    open suspend fun deleteAllAndInsert(items: List<AudioFileEntity>) {
        deleteAll()
        insertAll(items)
    }
}