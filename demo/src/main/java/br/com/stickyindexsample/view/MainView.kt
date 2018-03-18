package br.com.stickyindexsample.view

import android.Manifest
import android.app.SearchManager
import android.app.SearchManager.QUERY
import android.content.Intent
import android.content.Intent.ACTION_SEARCH
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import br.com.customsearchable.SearchActivity
import br.com.customsearchable.contract.CustomSearchableConstants.CLICKED_RESULT_ITEM
import br.com.customsearchable.model.ResultItem
import br.com.stickyindexsample.R
import br.com.stickyindexsample.view.adapter.ContactsAdapter
import com.karumi.dexter.Dexter.withActivity
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlinx.android.synthetic.main.main_activity.*


/**
 * Created by Edgar on 29/05/2015.
 */
class MainView : AppCompatActivity() {

    private var viewPagerAdapter: ContactsAdapter? = null
    private var isPageViewWithOffset = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar!!.elevation = 0f
        window.sharedElementExitTransition = Slide()
        window.sharedElementEnterTransition = Slide()
        requestPermission()
    }

    private fun requestPermission() {
        withActivity(this)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        setUpPageViewer()
                        setOnSearchableListener()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        finish()
                    }
                }).check()
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
            showTabs()
            isPageViewWithOffset = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_search -> {
                startSearchActivity()
                hideTabs()
                isPageViewWithOffset = true
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }


    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (ACTION_SEARCH == intent.action) {
            makeText(this, "typed: ${intent.getStringExtra(QUERY)}", LENGTH_SHORT).show()
        } else if (Intent.ACTION_VIEW == intent.action) {
            val receivedItem = intent.extras.getParcelable<ResultItem>(CLICKED_RESULT_ITEM)
            (viewPagerAdapter!!.getItem(1) as ContactsView).updateRecyclerViewFromSearchSelection(receivedItem.header)
        }
    }

    private fun setOnSearchableListener() {
        val searchDialog = getSystemService(SEARCH_SERVICE) as SearchManager
        val listener = { showTabs() }
        searchDialog.setOnCancelListener(listener)
        searchDialog.setOnDismissListener(listener)
    }

    private fun showTabs() {
        tabs.animate().translationY(0f)
        pager.animate().translationY(0f)
    }

    private fun hideTabs() {
        tabs.animate().translationY((-tabs!!.height).toFloat())
        pager.animate().translationY((-tabs!!.height).toFloat())
    }
}

