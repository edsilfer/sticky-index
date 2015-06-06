package br.com.stickyindexsample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.stickyindex.StickyIndex;
import br.com.stickyindexsample.adapter.RecyclerViewAdapter;
import br.com.stickyindexsample.contracts.AppConstants;
import br.com.stickyindexsample.dao.ContactsDAO;
import br.com.stickyindexsample.layout.RecyclerViewOnItemClickListener;
import br.com.stickyindexsample.model.Contact;

/**
 * Created by Edgar on 29/05/2015.
 */
public class ActivityA extends Activity  {

    private RecyclerView recyclerView;


    // Activity Callbacks __________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        getWindow().setSharedElementExitTransition(new Slide());
        getWindow().setSharedElementEnterTransition(new Slide());

        // Retrieves a list of Contacts from the phone
        List<Contact> myContacts = ContactsDAO.listMappedContacts();

        // Creates RecyclerView and its layout
        recyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(myContacts, this);
        recyclerView.setAdapter(adapter);
        implementsRecyclerViewOnItemClickListener();

        // Creates index viewer
        StickyIndex indexContainer = (StickyIndex) this.findViewById(R.id.sticky_index_container);
        // INSERT CHAR LIST
        indexContainer.setDataSet(getIndexList(myContacts));
        indexContainer.setReferenceList(recyclerView);

        /*// Creates and links FastScroller
        FastScroller fastScroller = (FastScroller) this.findViewById(R.id.fast_scroller);
        fastScroller.setRecyclerView(recyclerView);

        // Creates ScrollListener Observer
        RecyclerViewOnScrollListener listener = new RecyclerViewOnScrollListener(this);
        listener.setRecyRecyclerView(recyclerView);
        listener.register(fastScroller);*/
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
    private void implementsRecyclerViewOnItemClickListener () {

        final Activity act = this;

        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(this,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final View contactThumbnail = view.findViewById(R.id.contact_thumbnail);
                        Intent intent = new Intent(act, ActivityB.class);
                        Bundle b = new Bundle();
                        b.putParcelable(AppConstants.CONTACT_INFORMATION, ((RecyclerViewAdapter) recyclerView.getAdapter()).getContact(position));
                        intent.putExtras(b);

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(act, contactThumbnail, "contact_thumbnail");
                        startActivity(intent, options.toBundle());
                    }
                }));
    }

    private char[] getIndexList (List<Contact> list) {
        char[] result = new char[list.size()];
        int i = 0;

        for (Contact c : list) {
            result[i] = Character.toUpperCase(c.getName().charAt(0));
            i++;
        }

        return result;
    }
}

