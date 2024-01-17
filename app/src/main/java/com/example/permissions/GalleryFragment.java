package com.example.permissions;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GalleryFragment extends Fragment {
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        imageView = view.findViewById(R.id.img);
        if(getArguments()!=null) {
            final  String text = getArguments().getString( "gallerypath" );
            Uri imageuri = Uri.parse( text );
            handleSelectedImage( imageuri );
        }
        return view;
    }
    private void handleSelectedImage(Uri imageUri) {
        imageView.setImageURI(imageUri);
    }
}
