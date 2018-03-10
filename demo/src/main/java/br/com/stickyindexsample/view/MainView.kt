package br.com.stickyindexsample.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import br.com.customsearchable.SearchActivity
import br.com.customsearchable.contract.CustomSearchableConstants
import br.com.customsearchable.model.ResultItem
import br.com.stickyindexsample.R
import br.com.stickyindexsample.view.adapter.ContactsAdapter
import kotlinx.android.synthetic.main.main_activity.*

/**
 * Created by Edgar on 29/05/2015.
 */
class MainView : AppCompatActivity() {

    private var viewPagerAdapter: ContactsAdapter? = null
    private var isPageViewWithOffset = false

    // Activity Callbacks __________________________________________________________________________
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        this.supportActionBar!!.elevation = 0f

        window.sharedElementExitTransition = Slide()
        window.sharedElementEnterTransition = Slide()

        setUpPageViewer()
        setOnSearchableListener()
    }

    private fun setUpPageViewer() {
        viewPagerAdapter = ContactsAdapter(this.supportFragmentManager, this)
        pager.adapter = viewPagerAdapter
        tabs.shouldExpand = true
        tabs.textColor = resources.getColor(R.color.text_primary)
        tabs.dividerColor = resources.getColor(R.color.primary)
        tabs.setIndicatorColorResource(R.color.text_primary)
        tabs.indicatorHeight = 7
        tabs.setViewPager(pager)
        pager.currentItem = 1
    }

    public override fun onResume() {
        super.onResume()
        if (isPageViewWithOffset) {
            tabs.animate().translationY(0f)
            pager.animate().translationY(0f)
            isPageViewWithOffset = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        if (id == R.id.action_search) {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)

            tabs.animate().translationY((-tabs!!.height).toFloat())
            pager.animate().translationY((-tabs!!.height).toFloat())
            isPageViewWithOffset = true

            val contactFragmentRootView = (viewPagerAdapter!!.getItem(1) as ContactsView).rootView
            contactFragmentRootView.invalidate()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // SEARCHABLE INTERFACE ________________________________________________________________________
    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Toast.makeText(this, "typed: $query", Toast.LENGTH_SHORT).show()
        } else if (Intent.ACTION_VIEW == intent.action) {
            val bundle = this.intent.extras!!

            val receivedItem = bundle.getParcelable<ResultItem>(CustomSearchableConstants.CLICKED_RESULT_ITEM)
            (viewPagerAdapter!!.getItem(1) as ContactsView).updateRecyclerViewFromSearchSelection(receivedItem!!.header)
        }
    }

    private fun setOnSearchableListener() {
        val searchDialog = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchDialog.setOnCancelListener {
            tabs!!.animate().translationY(0f)
            pager.animate().translationY(0f)
        }

        searchDialog.setOnDismissListener {
            tabs!!.animate().translationY(0f)
            pager.animate().translationY(0f)
        }
    }
}

