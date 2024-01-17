package com.example.permissions;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ListView contactListView = view.findViewById(R.id.cl);
        ArrayList<String> contacts = getContacts();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, contacts);
        contactListView.setAdapter(adapter);
        return view;
    }

    private ArrayList<String> getContacts() {
        ArrayList<String> contactList = new ArrayList<>();
        Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = requireContext().getContentResolver().query(contactsUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactList.add(contactName);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return contactList;
    }
}
