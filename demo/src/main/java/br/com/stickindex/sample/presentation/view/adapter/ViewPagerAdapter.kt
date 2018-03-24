package br.com.stickindex.sample.presentation.view.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import br.com.stickindex.sample.presentation.view.ContactsView
import br.com.stickindex.sample.presentation.view.FavoritesView

/**
 * Created by Edgar on 30/04/2015.
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    companion object {
        private val CONTACTS_FRAGMENT = ContactsView()
        private val FAVORITES_FRAGMENT = FavoritesView()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "ALL CONTACTS"
            1 -> "FAVORITES"
            else -> "Unknown"
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): android.support.v4.app.Fragment {
        return when (position) {
            0 -> CONTACTS_FRAGMENT
            1 -> FAVORITES_FRAGMENT
            else -> CONTACTS_FRAGMENT
        }
    }
}
