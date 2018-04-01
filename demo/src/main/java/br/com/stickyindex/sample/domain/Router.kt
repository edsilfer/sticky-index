package br.com.stickyindex.sample.domain

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import br.com.customsearchable.SearchActivity
import br.com.stickyindex.sample.domain.model.Contact

/**
 * Encapsulates the logic for launching view inside the application
 */
class Router(private val context: AppCompatActivity) {

    /**
     * Launches {@link UserDetails} for the fiven contact
     */
    fun launchUserDetails(contact: Contact) {
//        val contactThumbnail = view.findViewById<View>(R.id.contact_thumbnail)
//        val pair1 = Pair.create(contactThumbnail, "contact_thumbnail")
//        val contactName = view.findViewById<View>(R.id.contact_name)
//        val pair2 = Pair.create(contactName, "contact_name")
//        Intent intent = new Intent(activity, ContactDetails.class);
//        Bundle b = new Bundle();
//        b.putParcelable(Constants.CONTACT_INFORMATION, contact);
//        intent.putExtras(b);
//        ActivityOptions options = ActivityOptions . makeSceneTransitionAnimation (activity, pair1, pair2);
//        activity.startActivity(intent, options.toBundle());
    }

    /**
     * Launches {@link SearchView}
     */
    fun launchSearchView() {
        doDefaultLaunching(Intent(context, SearchActivity::class.java))
    }

    private fun doDefaultLaunching(intent: Intent, finishCaller: Boolean = false) {
        context.startActivity(intent)
        if (finishCaller) {
            context.finish()
        }
    }
}