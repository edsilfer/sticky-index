package br.com.stickindex.sample.presentation.view.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.AsyncTask
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.stickindex.sample.R
import br.com.stickindex.sample.domain.model.Contact
import br.com.stickindex.sample.presentation.view.layout.TextGetter
import com.pkmmte.view.CircularImageView
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

/**
 * Created by Edgar on 30/05/2015.
 */
class ContactsAdapter(
        val dataSet: List<Contact>,
        private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), TextGetter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_details, parent, false)
        return ContactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contactHolder = holder as ContactsViewHolder
        contactHolder.contactName.text = dataSet[position].name
        contactHolder.firstLetter.text = dataSet[position].name[0].toString().toUpperCase()

        if (dataSet[position].thumbnail != null) {
            contactHolder.firstLetter.visibility = TextView.INVISIBLE
            object : AsyncTask<Uri, Int, Bitmap>() {
                override fun doInBackground(vararg params: Uri): Bitmap? {
                    try {
                        return MediaStore.Images.Media.getBitmap(context.contentResolver, params[0])
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                        return null
                    } catch (e: IOException) {
                        e.printStackTrace()
                        return null
                    }

                }

                override fun onPostExecute(b: Bitmap) {
                    contactHolder.thumbnail.setImageBitmap(b)
                }
            }.execute(dataSet[position].thumbnail)

            contactHolder.thumbnail.setImageURI(dataSet[position].thumbnail)
        } else {
            contactHolder.firstLetter.visibility = TextView.VISIBLE
            contactHolder.thumbnail.setImageResource(R.drawable.circle_icon)
            val drawable = contactHolder.thumbnail.drawable as GradientDrawable
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            drawable.setColor(color)
        }

        setRegularLineLayout(contactHolder)
    }


    private fun setFirstLineTextLayout(viewHolder: ContactsViewHolder) {
        viewHolder.firstLetter.text = "Set up my profile"
        viewHolder.firstLetter.setTextColor(Color.parseColor("#000000"))
        viewHolder.firstLetter.textSize = 18f
        viewHolder.contactName.text = ""
        viewHolder.thumbnail.visibility = CircularImageView.INVISIBLE
    }

    private fun setRegularLineLayout(vh: ContactsViewHolder) {
        vh.firstLetter.setTextColor(parseColor("#ffffff"))
        vh.firstLetter.textSize = 26f
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun getContact(pos: Int): Contact {
        return dataSet[pos]
    }

    fun getContactByName(name: String): Contact? {
        return dataSet.first { it.name == name }
    }

    override fun getTextFromAdapter(pos: Int): String {
        return dataSet[pos].name[0].toString().toUpperCase()
    }

    inner class ContactsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var firstLetter: TextView = v.findViewById<View>(R.id.contact_first_letter) as TextView
        internal var contactName: TextView = v.findViewById<View>(R.id.contact_name) as TextView
        internal var thumbnail: CircularImageView = v.findViewById<View>(R.id.contact_thumbnail) as CircularImageView

    }
}
