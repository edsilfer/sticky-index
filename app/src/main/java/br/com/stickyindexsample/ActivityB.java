package br.com.stickyindexsample;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import br.com.stickyindexsample.contracts.AppConstants;
import br.com.stickyindexsample.model.Contact;

/**
 * Created by Edgar on 29/05/2015.
 */
public class ActivityB extends Activity {

    private ImageView contactCover;
    private CircularImageView contactThumbnail;
    private TextView contactId;
    private TextView contactName;

    // Activity Callback ___________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        contactCover = (ImageView) this.findViewById(R.id.contact_cover);
        contactThumbnail = (CircularImageView) this.findViewById(R.id.contact_thumbnail_details);
        contactId = (TextView) this.findViewById(R.id.contact_id_details_content);
        contactName = (TextView) this.findViewById(R.id.contact_name_content);

        getWindow().setReturnTransition(createScreenSplitAnimation());

        Bundle bundle = this.getIntent().getExtras();

        assert (bundle != null);

        if (bundle != null) {
            Contact contact = bundle.getParcelable(AppConstants.CONTACT_INFORMATION);

            contactId.setText(contact.get_id());
            contactName.setText(contact.getName());

            Picasso.with(this)
                    .load(randCover())
                    .placeholder(R.drawable.cover1)
                    .error(R.drawable.cover1)
                    .fit()
                    .centerCrop()
                    .into(contactCover);

            assert (contact.getThumbnail() != null);

            Picasso.with(this)
                    .load(contact.getThumbnail())
                    .placeholder(R.drawable.circle_icon)
                    .error(R.drawable.circle_icon)
                    .fit()
                    .centerCrop()
                    .into(contactThumbnail);
        }
    }

    // MENU ________________________________________________________________________________________
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_a, menu);
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
    private Integer randCover () {
        int aux = (int) (Math.random()*4);

        switch (aux) {
            case 0:
                return R.drawable.cover1;
            case 1:
                return R.drawable.cover2;
            case 2:
                return R.drawable.cover3;
            case 3:
                return R.drawable.cover4;
            default :
                return R.drawable.cover1;
        }
    }

    private TransitionSet createScreenSplitAnimation () {
        Slide slideTop = new Slide(Gravity.TOP);
        Slide slideDown = new Slide(Gravity.BOTTOM);

        slideTop.addTarget(R.id.contact_cover);
        slideDown.addTarget(R.id.contact_info_details);

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(slideTop);
        transitionSet.addTransition(slideDown);

        return transitionSet;
    }
}
