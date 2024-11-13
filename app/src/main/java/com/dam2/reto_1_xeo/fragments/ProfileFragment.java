package com.dam2.reto_1_xeo.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dam2.reto_1_xeo.R;
import com.dam2.reto_1_xeo.activities.MainActivity;
import com.dam2.reto_1_xeo.models.LoginResponse.UserData;
import com.dam2.reto_1_xeo.utils.SharedPreferencesHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {

    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private Uri photoURI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        rootView.findViewById(R.id.backButton).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigateUp();
            ((MainActivity) requireActivity()).showNavigation();
        });

        View avatarChangeLinearLayout = rootView.findViewById(R.id.changeAvatarLinearLayout);
        avatarChangeLinearLayout.setOnClickListener(v -> abrirCamara());

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView usernameTextView = view.findViewById(R.id.usernameTextView);
        TextView emailTextView = view.findViewById(R.id.emailTextView);
        TextView addressTextView = view.findViewById(R.id.addressTextView);
        ImageView profileImageView = view.findViewById(R.id.profileImageView);

        UserData userData = SharedPreferencesHelper.getUserData(requireActivity());

        if (userData != null) {
            usernameTextView.setText(userData.getNombre() + " " + userData.getApellido1() + " " + userData.getApellido2());
            emailTextView.setText(userData.getCorreo());

            String address = userData.getCalle() + " " + userData.getNumero();
            addressTextView.setText(address);

            Glide.with(this)
                    .load(userData.getFoto())
                    .error(R.drawable.obras)
                    .into(profileImageView);
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.e("ProfileFragment", "Error al crear el archivo de la foto", ex);
        }

        if (photoFile != null) {
            ContentValues values = new ContentValues();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            String date = dateFormat.format(new Date());

            values.put(MediaStore.Images.Media.TITLE, "Avatar_" + date + ".jpg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Avatar photo taken on " + System.currentTimeMillis());
            photoURI = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Avatar_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), photoURI);
                guardarFoto(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void guardarFoto(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        File carpetaGaleria = new File(requireActivity().getFilesDir(), "Galeria");
        if (!carpetaGaleria.exists()) {
            if (!carpetaGaleria.mkdirs()) {
                Toast.makeText(requireContext(), "No se pudo crear la carpeta", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = dateFormat.format(new Date());
        String filename = "Avatar_" + date + ".jpg";
        File file = new File(carpetaGaleria, filename);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteArray);
            fos.close();
            Toast.makeText(requireContext(), "Avatar guardado", Toast.LENGTH_SHORT).show();

            // Aquí puedes actualizar la vista con la nueva imagen de avatar.
            Glide.with(this)
                    .load(file)
                    .into((android.widget.ImageView) requireView().findViewById(R.id.profileImageView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {}
    );

    private void checkExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}