package br.com.sample.presentation.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import br.com.sample.R

/**
 * Created by edgar on 6/8/15.
 */
class ContactsDetailsAdapter(context: Context) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
            0 -> setIconAndContent(holder, R.drawable.camera_icon, "nickname")
            1 -> setIconAndContent(holder, R.drawable.home_number_icon, "username@email.com")
            2 -> setIconAndContent(holder, R.drawable.mobile_number_icon, "(+55) 61 7849 - 2136")
            3 -> setIconAndContent(holder, R.drawable.home_number_icon, "(+55) 12 3541 - 6589")
        }

        return view!!
    }

    private fun setIconAndContent(holder: ViewHolder, icon: Int, content: String) {
        holder.icon.setImageResource(icon)
        holder.content.text = content
    }

    override fun getCount() = 4

    override fun getItem(position: Int): Any = position

    override fun getItemId(position: Int): Long = position.toLong()

    private inner class ViewHolder(v: View) {
        val icon: ImageView = v.findViewById<View>(R.id.dtls_type) as ImageView
        val content: TextView = v.findViewById<View>(R.id.dtls_content) as TextView
    }
}
