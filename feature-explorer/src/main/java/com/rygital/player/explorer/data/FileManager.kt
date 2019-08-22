package com.rygital.player.explorer.data

import android.content.Context
import android.provider.MediaStore
import com.rygital.core.di.ApplicationContext
import com.rygital.core.model.AudioFile
import javax.inject.Inject

interface FileManager {
    fun getAllAudioFilesFromStorage(): List<AudioFile>
}

class FileManagerImpl @Inject constructor(
        @ApplicationContext private val context: Context
) : FileManager {

    override fun getAllAudioFilesFromStorage(): List<AudioFile> {
        val cursor = context.contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.TITLE + " ASC"
        )

        cursor ?: return emptyList()

        val titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val pathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)

        val songs = mutableListOf<AudioFile>()
        while (cursor.moveToNext()) {
            val title: String? = cursor.getString(titleIndex)
            val path: String? = cursor.getString(pathIndex)


            if (path != null && title != null) {
                songs.add(AudioFile(path, title))
            }
        }
        cursor.close()

        return songs
    }
}