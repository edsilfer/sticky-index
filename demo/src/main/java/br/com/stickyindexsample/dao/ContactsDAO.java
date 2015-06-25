package br.com.stickyindexsample.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.com.stickyindexsample.layout.AppContext;
import br.com.stickyindexsample.model.Contact;

/**
 * Created by Edgar on 29/05/2015.
 */
public class ContactsDAO {
    // LIST ________________________________________________________________________________________
    public static List<Contact> listMappedContacts() {
        Set<Contact> result = new TreeSet<>();

        Cursor cursor = listContacts();

        if ((cursor != null) && cursor.moveToFirst()) {
            String previous = "";
            Contact actual = null;

            do {
                result.add(mapContact(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return new ArrayList<>(result);
    }

    private static Boolean isHeader(String n1, String n2) {
        if (Character.toLowerCase(n1.charAt(0)) != Character.toLowerCase(n2.charAt(0))) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private static Cursor listContacts() {
        ContentResolver contentResolver = AppContext.getAppContext().getContentResolver();

        // Sets the columns to retrieve for the user profile
        String[] projection = new String[]
                {
                        ContactsContract.Profile._ID,
                        ContactsContract.Profile.DISPLAY_NAME_PRIMARY,
                        ContactsContract.Profile.LOOKUP_KEY,
                        ContactsContract.Profile.PHOTO_THUMBNAIL_URI
                };

        String selection = "";
        String[] selectionArgs = null;
        String sortOrder = "LOWER (" + ContactsContract.Profile.DISPLAY_NAME_PRIMARY + ") ASC";

        // Retrieves the profile from the Contacts Provider
        return contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder);
    }

    // UTIL ________________________________________________________________________________________
    private static Contact mapContact(Cursor c) {
        Integer _ID = c.getColumnIndex(ContactsContract.Profile._ID);
        Integer DISPLAY_NAME_PRIMARY = c.getColumnIndex(ContactsContract.Profile.DISPLAY_NAME_PRIMARY);
        Integer PHOTO_THUMBNAIL_URI = c.getColumnIndex(ContactsContract.Profile.PHOTO_THUMBNAIL_URI);

        Contact contact = new Contact();

        contact.set_id(c.getString(_ID));
        contact.setName(c.getString(DISPLAY_NAME_PRIMARY));
        contact.setThumbnail(c.getString(PHOTO_THUMBNAIL_URI));

        return contact;
    }
}