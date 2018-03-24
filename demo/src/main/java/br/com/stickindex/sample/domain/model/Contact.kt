package br.com.stickindex.sample.domain.model

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import io.reactivex.Single

/**
 * Created by Edgar on 29/05/2015.
 */
data class Contact(
        val id: String,
        val name: String,
        val thumbnail: Uri?
) : Comparable<Contact> {

    override fun equals(other: Any?): Boolean {
        return other is Contact && this.name.equals(other.name, ignoreCase = true)
    }

    override fun compareTo(other: Contact): Int {
        return this.name.compareTo(other.name, ignoreCase = true)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (thumbnail?.hashCode() ?: 0)
        return result
    }

    fun getThumbnailAsBitmap(context: Context): Single<Bitmap> =
            Single.fromCallable {
                MediaStore.Images.Media.getBitmap(context.contentResolver, thumbnail)
            }
}
