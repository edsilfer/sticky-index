package br.com.stickyindexsample.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import br.com.stickyindexsample.R

/**
 * Created by edgar on 6/8/15.
 */
class ContactsDetailsAdapter(context: Context) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

    // GETTERS AND SETTERS _________________________________________________________________________
    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder

        var view = convertView
        if (convertView == null) {
            view = inflater.inflate(R.layout.contact_dtls_row, null)
            holder = ViewHolder(view)
            view?.tag = holder
        } else {
            holder = view?.tag as ViewHolder
        }

        when (position) {
            0 -> {
                holder.icon.setImageResource(R.drawable.camera_icon)
                holder.content.text = "usrnickname"
            }
            1 -> {
                holder.icon.setImageResource(R.drawable.home_number_icon)
                holder.content.text = "username@email.com"
            }
            2 -> {
                holder.icon.setImageResource(R.drawable.mobile_number_icon)
                holder.content.text = "(+55) 61 7849 - 2136"
            }
            3 -> {
                holder.icon.setImageResource(R.drawable.home_number_icon)
                holder.content.text = "(+55) 12 3541 - 6589"
            }
        }

        return view!!
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private inner class ViewHolder(v: View) {
        val icon: ImageView = v.findViewById<View>(R.id.dtls_type) as ImageView
        val content: TextView = v.findViewById<View>(R.id.dtls_content) as TextView
    }
}
