package br.com.sample.domain.model

import android.net.Uri

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
}
