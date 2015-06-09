package br.com.stickyindexsample;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import br.com.stickyindexsample.adapter.ContactsDtlsAdapter;
import br.com.stickyindexsample.contracts.AppConstants;
import br.com.stickyindexsample.model.Contact;

/**
 * Created by Edgar on 29/05/2015.
 */
public class ActivityB extends Activity {

    private ImageView contactCover;
    private CircularImageView contactThumbnail;
    private TextView contactName;
    private ListView contactDtls;

    // Activity Callback ___________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        contactCover = (ImageView) this.findViewById(R.id.dtls_contact_cover);
        contactThumbnail = (CircularImageView) this.findViewById(R.id.dtls_contact_thumbnail);
        contactName = (TextView) this.findViewById(R.id.dtls_contact_name);
        contactDtls = (ListView) this.findViewById(R.id.dtls_contact_list);

        ContactsDtlsAdapter adapter = new ContactsDtlsAdapter(this);
        contactDtls.setAdapter(adapter);

        getWindow().setReturnTransition(new Fade ());
        getWindow().setEnterTransition(createEnterTransition());

        Bundle bundle = this.getIntent().getExtras();

        assert (bundle != null);

        if (bundle != null) {
            Contact contact = bundle.getParcelable(AppConstants.CONTACT_INFORMATION);
            contactName.setText(contact.getName());

            Picasso.with(this)
                    .load(R.drawable.cover4)
                    .placeholder(R.drawable.cover4)
                    .fit()
                    .centerCrop()
                    .into(contactCover);

            Picasso.with(this)
                    .load(contact.getThumbnail())
                    .fit()
                    .error(R.drawable.circle_icon)
                    .centerCrop()
                    .into(contactThumbnail);
        }
    }

    // MENU ________________________________________________________________________________________
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_search) {
            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // Util ________________________________________________________________________________________
    private TransitionSet createEnterTransition () {
        Slide s = new Slide(Gravity.BOTTOM);
        s.addTarget(R.layout.contact_details);

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(s);
        transitionSet.addTransition(new Fade());

        return transitionSet;
    }
}
