package br.com.sample.data

import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import br.com.sample.domain.model.Contact

/**
 * Created by edgarsf on 18/03/2018.
 */
object ContactMapper {
    fun map(cursor: Cursor): Contact {
        return Contact(
                cursor.getString(cursor.getColumnIndex(ContactsContract.Profile._ID)) ?: "",
                cursor.getString(cursor.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME_PRIMARY))
                        ?: "",
                mapThumbnail(cursor)
        )
    }

    private fun mapThumbnail(cursor: Cursor): Uri? {
        val thumbnailIndex = cursor.getColumnIndex(ContactsContract.Profile.PHOTO_THUMBNAIL_URI)
        val thumbnail = cursor.getString(thumbnailIndex)
        return if (thumbnail == null || thumbnail.isEmpty()) null else Uri.parse(thumbnail)
    }
}