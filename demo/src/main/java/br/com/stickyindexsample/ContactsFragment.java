package br.com.stickyindexsample;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.stickyindex.StickyIndex;
import br.com.stickyindexsample.adapter.RecyclerViewAdapter;
import br.com.stickyindexsample.contracts.AppConstants;
import br.com.stickyindexsample.dao.ContactsDAO;
import br.com.stickyindexsample.layout.FastScroller;
import br.com.stickyindexsample.layout.RecyclerViewOnItemClickListener;
import br.com.stickyindexsample.model.Contact;

/**
 * Created by edgar on 6/7/15.
 */
public class ContactsFragment extends Fragment {

    private Activity mActivity;
    private RecyclerView recyclerView;
    private List<Contact> myContacts;
    private FloatingActionButton fab;

    // CALLBACKS ___________________________________________________________________________________
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieves a list of Contacts from the phone
        myContacts = ContactsDAO.listMappedContacts();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView               = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        FastScroller fastScroller  = (FastScroller) rootView.findViewById(R.id.fast_scroller);
        StickyIndex indexContainer = (StickyIndex) rootView.findViewById(R.id.sticky_index_container);
        fab                        = (FloatingActionButton) rootView.findViewById(R.id.fab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(myContacts, mActivity);
        recyclerView.setAdapter(adapter);
        implementsRecyclerViewOnItemClickListener();

        indexContainer.setDataSet(getIndexList(myContacts));
        indexContainer.setOnScrollListener(recyclerView);
        indexContainer.subscribeForScrollListener(fastScroller);

        fastScroller.setRecyclerView(recyclerView);
        fastScroller.setStickyIndex(indexContainer.getStickyIndex());

        implementFabListener();

        return rootView;
    }

    // Util ________________________________________________________________________________________
    private void implementsRecyclerViewOnItemClickListener () {
        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(mActivity,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final View contactThumbnail = view.findViewById(R.id.contact_thumbnail);
                        final Pair<View, String> pair1 = Pair.create(contactThumbnail, "contact_thumbnail");

                        final View contactName = view.findViewById(R.id.contact_name);
                        final Pair<View, String> pair2 = Pair.create(contactName, "contact_name");


                        Intent intent = new Intent(mActivity, ActivityB.class);
                        Bundle b = new Bundle();
                        b.putParcelable(AppConstants.CONTACT_INFORMATION, ((RecyclerViewAdapter) recyclerView.getAdapter()).getContact(position));
                        intent.putExtras(b);

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, pair1, pair2);
                        mActivity.startActivity(intent, options.toBundle());
                    }
                }));
    }

    private void implementFabListener () {
        final Activity helper = getActivity();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(helper, "FAB was clicked!", Toast.LENGTH_SHORT).show();
            }
        });
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
