package br.com.stickyindex.sample.domain.model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.Single

/**
 * User phone's contact represention in the context of this application
 */
data class Contact(
        val id: String,
        val name: String,
        val thumbnail: Uri?
) : Comparable<Contact> {

    /**
     * {@inheritDoc}
     */
    override fun equals(other: Any?): Boolean {
        return other is Contact && this.name.equals(other.name, ignoreCase = true)
    }

    /**
     * {@inheritDoc}
     */
    override fun compareTo(other: Contact): Int {
        return this.name.compareTo(other.name, ignoreCase = true)
    }

    /**
     * {@inheritDoc}
     */
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        return result
    }

    /**
     * Converts contact's thumbnail URI into a bitmap that can be loaded in an
     */
    fun getThumbnailAsBitmap(context: Context): Single<Bitmap> =
            Single.fromCallable {
                MediaStore.Images.Media.getBitmap(context.contentResolver, thumbnail)
            }
}
