package br.com.stickyindex.sample.presentation.presenter

import android.Manifest
import android.app.SearchManager.QUERY
import android.arch.lifecycle.Lifecycle
import android.content.Intent
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import br.com.edsilfer.toolkit.core.components.BasePresenter
import br.com.stickyindex.sample.domain.Router
import br.com.stickyindex.sample.presentation.view.MainView
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener

/**
 * Encapsulates the business logic triggered from {@link MainView}
 */
class MainPresenter(
        lifecycle: Lifecycle,
        private val view: MainView,
        private val router: Router
) : BasePresenter(lifecycle) {

    /**
     * {@inheritDoc}
     */
    override fun onStart() {
        super.onStart()
        requestReadContactsPermission()
    }

    private fun requestReadContactsPermission() {
        Dexter.withActivity(view)
                .withPermission(Manifest.permission.READ_CONTACTS)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        view.render()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        view.finish()
                    }
                }).check()
    }

    /**
     * Handle a click event on menu search
     */
    fun onMenuSearchClick(): Boolean {
        router.launchSearchView()
        return true
    }

    /**
     * Handles a click event on menu settings
     */
    fun onMenuSettingsClick(): Boolean = true

    /**
     * Handles the action result for search action
     */
    fun onSearchResult(intent: Intent) {
        val query = intent.getStringExtra(QUERY)
        makeText(view, "User searched for: $query", LENGTH_SHORT).show()
    }
}