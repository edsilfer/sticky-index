package br.com.stickyindex.sample.presentation.view.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.stickyindex.sample.presentation.view.ContactsView
import br.com.stickyindex.sample.presentation.view.FavoritesView

/**
 * Encapsulates the logic for assembling a {@link ViewPager} and its child {@link Fragments}
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    companion object {
        private val CONTACTS_FRAGMENT = ContactsView()
        private val FAVORITES_FRAGMENT = FavoritesView()
    }

    /**
     * {@inheritDoc}
     */
    override fun getPageTitle(position: Int) = when (position) {
        0 -> "ALL CONTACTS"
        1 -> "FAVORITES"
        else -> "Unknown"
    }

    /**
     * {@inheritDoc}
     */
    override fun getCount() = 2

    /**
     * {@inheritDoc}
     */
    override fun getItem(position: Int) = when (position) {
        0 -> CONTACTS_FRAGMENT
        1 -> FAVORITES_FRAGMENT
        else -> CONTACTS_FRAGMENT
    }
}
