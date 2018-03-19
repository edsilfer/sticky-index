package br.com.sample.domain

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import br.com.customsearchable.SearchActivity
import br.com.sample.domain.model.Contact

/**
 * Created by edgarsf on 18/03/2018.
 */
class Router(private val context: AppCompatActivity) {

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