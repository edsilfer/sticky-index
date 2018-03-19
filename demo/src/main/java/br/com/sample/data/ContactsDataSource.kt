package br.com.sample.data

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import br.com.sample.data.ContactMapper.map
import br.com.sample.domain.model.Contact
import java.util.*

/**
 * Created by Edgar on 29/05/2015.
 */
class ContactsDataSource(private val context: Context) {

    companion object {
        private const val DEFAULT_SELECTION = "${ContactsContract.Contacts.IN_VISIBLE_GROUP} = '1'"
        private const val DEFAULT_SORTING = "LOWER (${ContactsContract.Profile.DISPLAY_NAME_PRIMARY}) ASC"
    }

    fun listMappedContacts(): List<Contact> {
        val result = TreeSet<Contact>()
        val cursor = list()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                result.add(map(cursor))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return ArrayList(result)
    }

    fun searchByName(query: String): List<Contact> {
        val result = TreeSet<Contact>()
        val cursor = listByName(query)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                result.add(map(cursor))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return ArrayList(result)
    }

    private fun listByName(query: String): Cursor? {
        val selection = "${ContactsContract.Profile.DISPLAY_NAME_PRIMARY}  LIKE ? AND ${ContactsContract.Contacts.IN_VISIBLE_GROUP} = '1'"
        return context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                getProjection(),
                "$selection AND ${ContactsContract.Contacts.HAS_PHONE_NUMBER}",
                arrayOf("%$query%"),
                DEFAULT_SORTING)
    }

    private fun list(): Cursor? {
        return context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                getProjection(),
                "$DEFAULT_SELECTION AND ${ContactsContract.Contacts.HAS_PHONE_NUMBER}",
                null,
                DEFAULT_SORTING
        )
    }

    private fun getProjection(): Array<String> {
        return arrayOf(
                ContactsContract.Profile._ID,
                ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
                ContactsContract.Profile.LOOKUP_KEY,
                ContactsContract.Profile.PHOTO_THUMBNAIL_URI
        )
    }
}