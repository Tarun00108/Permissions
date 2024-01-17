package com.example.permissions;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class CameraFragment extends Fragment {

    private ImageView imageView;
    private Button btnCapture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView = view.findViewById(R.id.imageview);
        //btnCapture = view.findViewById(R.id.btnCapture);
        if (getArguments() != null) {
            Bitmap bitmap = getArguments().getParcelable("bitmapData");
            if (bitmap != null) {
                handleImage(bitmap);
            } else {
                // Handle the case where no Bitmap data is provided
                Toast.makeText(requireContext(), "No Bitmap data available", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case where no data is provided
            Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void handleImage(Bitmap imageBitmap) {
        imageView.setImageBitmap(imageBitmap);
    }
}

