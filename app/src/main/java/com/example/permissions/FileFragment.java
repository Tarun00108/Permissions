package com.example.permissions;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
public class FileFragment extends Fragment {
    private ImageView image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate( R.layout.fragment_file, container, false );
        image = view.findViewById(R.id.selectedFilePathTextView);
        if(getArguments()!=null) {
            final  String text = getArguments().getString( "path" );
            Uri imageuri = Uri.parse( text );
            handleimage( imageuri );
        }
        return view;
    }
    private void handleimage(Uri imageuri) {
        image.setImageURI( imageuri );
    }
}