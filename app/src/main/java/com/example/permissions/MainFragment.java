package com.example.permissions;

import static android.app.Activity.RESULT_OK;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainFragment extends Fragment {
    Button camera, contacts, permission, gallery, filemanager;
    private static final int CONTACT_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CODE_PERMISSION = 124;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 200;
    private static final int REQUEST_PERMISSION_CODE = 123;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1002;
    private static final int REQUEST_CODE_PICK_IMAGE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_main, container, false );
        contacts = view.findViewById( R.id.CT );
        permission = view.findViewById( R.id.Pm );
        camera = view.findViewById( R.id.CM );
        filemanager = view.findViewById( R.id.FM );
        gallery = view.findViewById( R.id.GY );
        permission.setVisibility( View.VISIBLE );
        gallery.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_CODE_PICK_IMAGE);
                } else {
                    ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION );
                }
            }
        } );
        contacts.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ContextCompat.checkSelfPermission( requireContext(), Manifest.permission.READ_CONTACTS ) == PackageManager.PERMISSION_GRANTED) {
                    showContacts();
                } else {
                    ActivityCompat.requestPermissions( requireActivity(), new String[]{Manifest.permission.READ_CONTACTS}, CONTACT_PERMISSION_REQUEST_CODE );
                }
            }
        } );
        filemanager.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission( requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent( Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, 123);
                } else {
                    requestPermissions( new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE );
                }
            }
        } );
        camera.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission( requireContext(), Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    ActivityCompat.requestPermissions( requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE );
                }
            }
        } );
        permission.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                CAMERA_PERMISSION_REQUEST_CODE);

                    }

                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                STORAGE_PERMISSION_REQUEST_CODE);
                    }

                    // Add more conditions for other permissions as needed
                }
            }
        } );

        return view;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            // Handle camera permission request result
            Toast.makeText(getContext() , "done", Toast.LENGTH_SHORT ).show();
        } else if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            // Handle storage permission request result
        }
        // Add more conditions for other permission request results as needed
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == 123 && data != null) {
            final Bundle bundle = new Bundle();
            Uri selectedImageUri = data.getData();
            bundle.putString("path",  selectedImageUri.toString() );
            NavController  navController = Navigation.findNavController( requireView() );
            navController.navigate( R.id.fileFragment, bundle );
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE && data != null) {
            final Bundle bundle = new Bundle();
            Uri ImageUri = data.getData();
            bundle.putString("gallerypath",  ImageUri.toString() );
            NavController  navController = Navigation.findNavController( requireView() );
            navController.navigate( R.id.galleryFragment, bundle );
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bundle bundle = new Bundle();
                bundle.putParcelable("bitmapData", imageBitmap);
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.cameraFragment, bundle);
            }
        }
    }
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private void showContacts() {
        NavController  navController = Navigation.findNavController( requireView() );
        navController.navigate( R.id.contactFragment );
    }
}