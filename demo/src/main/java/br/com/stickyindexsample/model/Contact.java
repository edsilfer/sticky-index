package br.com.stickyindexsample.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Edgar on 29/05/2015.
 */
public class Contact implements Parcelable, Comparable<Contact> {
    private String _id;
    private String name;
    private Uri thumbnail;

    public Contact () {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id != null ? _id : "?";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name : "Unkown Name";
    }

    public Uri getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        if (thumbnail != null) {
            this.thumbnail = Uri.parse(thumbnail);
        } else {
            this.thumbnail = null;
        }
    }

    // Parcelable Interface Implementation _________________________________________________________
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(thumbnail != null ? thumbnail.toString() : "");
    }

    // Parcelable Creator Implementation ___________________________________________________________
    public static final Creator<Contact> CREATOR = new Creator<Contact>() {

        public Contact createFromParcel(Parcel in) {
            Contact contact = new Contact();

            contact.set_id(in.readString());
            contact.setName(in.readString());
            contact.setThumbnail(in.readString());

            return contact;
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    // _____________________________________________________________________________________________
    @Override
    public boolean equals (Object o) {
        if ((o instanceof Contact) && (this.name.equalsIgnoreCase(((Contact) o).getName()))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Contact another) {
        return this.getName().compareToIgnoreCase(another.getName());
    }
}
