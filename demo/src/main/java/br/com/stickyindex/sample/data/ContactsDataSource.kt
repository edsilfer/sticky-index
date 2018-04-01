package br.com.stickyindex.sample.data

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract.Contacts.CONTENT_URI
import android.provider.ContactsContract.Contacts.HAS_PHONE_NUMBER
import android.provider.ContactsContract.Contacts.IN_VISIBLE_GROUP
import android.provider.ContactsContract.Profile.DISPLAY_NAME_PRIMARY
import android.provider.ContactsContract.Profile.LOOKUP_KEY
import android.provider.ContactsContract.Profile.PHOTO_THUMBNAIL_URI
import android.provider.ContactsContract.Profile._ID
import br.com.stickyindex.sample.data.ContactMapper.map
import br.com.stickyindex.sample.domain.model.Contact
import io.reactivex.Maybe
import java.util.*

/**
 * Provides CRUD method for manipulation user's phone contacts
 */
class ContactsDataSource(private val context: Context) {
    companion object {
        private val PROJECTION = arrayOf(
                _ID,
                DISPLAY_NAME_PRIMARY,
                LOOKUP_KEY,
                PHOTO_THUMBNAIL_URI
        )
    }

    /**
     * List all contacts in the phone
     */
    fun list(): Maybe<List<Contact>> =
            Maybe.defer {
                Maybe.create<List<Contact>> {
                    val result = TreeSet<Contact>()
                    val cursor = listContacts()
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            result.add(map(cursor))
                        } while (cursor.moveToNext())
                        cursor.close()
                        it.onSuccess(ArrayList(result))
                    }
                }
            }

    private fun listContacts(): Cursor? {
        return context.contentResolver.query(
                CONTENT_URI,
                PROJECTION,
                "$IN_VISIBLE_GROUP = '1' AND $HAS_PHONE_NUMBER",
                null,
                "LOWER ($DISPLAY_NAME_PRIMARY) ASC"
        )
    }
}