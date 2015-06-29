package br.com.stickyindexsample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import br.com.stickyindexsample.R;
import br.com.stickyindexsample.layout.TextGetter;
import br.com.stickyindexsample.model.Contact;

/**
 * Created by Edgar on 30/05/2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TextGetter {

    private Context context;
    private List<Contact> dataSet;

    // Constructor _________________________________________________________________________________
    public RecyclerViewAdapter (List<Contact> contacts, Context c) {
        this.dataSet = contacts;
        this.context = c;
    }

    // Callbacks ___________________________________________________________________________________
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_details, parent, false);

        return new ContactsViewHolder (view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ContactsViewHolder contactHolder = (ContactsViewHolder) holder;

        contactHolder.contactName.setText(dataSet.get(position).getName());
        contactHolder.firstLetter.setText(String.valueOf(dataSet.get(position).getName().charAt(0)).toUpperCase());

        if (dataSet.get(position).getThumbnail() != null) {
            contactHolder.firstLetter.setVisibility(TextView.INVISIBLE);

            new AsyncTask<Uri, Integer, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Uri... params) {
                    try {
                        return MediaStore.Images.Media.getBitmap(context.getContentResolver(), params[0]);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Bitmap b) {
                    contactHolder.thumbnail.setImageBitmap(b);
                }
            }.execute(dataSet.get(position).getThumbnail());

            contactHolder.thumbnail.setImageURI(dataSet.get(position).getThumbnail());
        } else {
            contactHolder.firstLetter.setVisibility(TextView.VISIBLE);
            contactHolder.thumbnail.setImageResource(R.drawable.circle_icon);

            GradientDrawable drawable = (GradientDrawable) contactHolder.thumbnail.getDrawable();
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            drawable.setColor(color);
        }

        setRegularLineLayout(contactHolder);
    }


    // Getters and Setters _________________________________________________________________________
    private void setFirstLineTextLayout (ContactsViewHolder vh) {
        vh.firstLetter.setText("Set up my profile");
        vh.firstLetter.setTextColor(Color.parseColor("#000000"));
        vh.firstLetter.setTextSize(18);
        vh.contactName.setText("");
        vh.thumbnail.setVisibility(CircularImageView.INVISIBLE);
    }

    private void setRegularLineLayout(ContactsViewHolder vh) {
        vh.firstLetter.setTextColor(Color.parseColor("#ffffff"));
        vh.firstLetter.setTextSize(26);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public Contact getContact (int pos) {
        return dataSet.get(pos);
    }

    public Contact getContactByName (String name) {
        for (Contact c : dataSet) {
            if (name.equals(c.getName())) {
                return c;
            }
        }

        return null;
    }

    public List<Contact> getDataSet() {
        return dataSet;
    }

    // Bubbler Filler ______________________________________________________________________________
    @Override
    public String getTextFromAdapter(int pos) {
        return String.valueOf(dataSet.get(pos).getName().charAt(0)).toUpperCase();
    }

    // ViewHolder class ____________________________________________________________________________
    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView firstLetter;
        TextView contactName;
        CircularImageView thumbnail;

        public ContactsViewHolder (View v) {
            super (v);
            firstLetter = (TextView) v.findViewById(R.id.contact_first_letter);
            contactName = (TextView) v.findViewById(R.id.contact_name);
            thumbnail = (CircularImageView) v.findViewById(R.id.contact_thumbnail);
        }
    }
}
