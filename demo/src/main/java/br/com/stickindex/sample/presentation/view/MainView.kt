package br.com.stickindex.sample.presentation.view

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEARCH
import android.content.Intent.ACTION_VIEW
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import br.com.customsearchable.contract.CustomSearchableConstants.CLICKED_RESULT_ITEM
import br.com.customsearchable.model.ResultItem
import br.com.stickindex.sample.R
import br.com.stickindex.sample.presentation.presenter.MainPresenter
import br.com.stickindex.sample.presentation.view.adapter.ViewPagerAdapter
import dagger.android.AndroidInjection.inject
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject


/**
 * Created by Edgar on 29/05/2015.
 */
class MainView : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, MainView::class.java)
    }

    @Inject
    lateinit var presenter: MainPresenter

    private var viewPagerAdapter: ViewPagerAdapter? = null

    /**
     * {@inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.elevation = 0f
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
    }

    fun render() {
        pager.adapter = viewPagerAdapter
    }

    /**
     * {@inheritDoc}
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * {@inheritDoc}
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> presenter.onMenuSettingsClick()
            R.id.action_search -> presenter.onMenuSearchClick()
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        when (intent.action) {
            ACTION_SEARCH -> presenter.onSearchResult(intent)
            ACTION_VIEW -> handleActionView(intent)
        }
    }

    private fun handleActionView(intent: Intent) {
        val receivedItem = intent.extras.getParcelable<ResultItem>(CLICKED_RESULT_ITEM)
        val contactView = viewPagerAdapter!!.getItem(1) as ContactsView
        contactView.scrollToContact(receivedItem.header)
    }
}

