package com.dam2.reto_1_xeo.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.dam2.reto_1_xeo.R;

public class SupportFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        ImageButton backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        TextView consolePhone = view.findViewById(R.id.console_support_phone);
        TextView consoleEmail = view.findViewById(R.id.console_support_email);

        consolePhone.setOnClickListener(v -> {
            String phoneNumber = getString(R.string.console_support_phone);
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
        });

        consoleEmail.setOnClickListener(v -> {
            String emailAddress = getString(R.string.console_support_email);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + emailAddress));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Técnico - Consolas");
            startActivity(emailIntent);
        });

        TextView smartPhone = view.findViewById(R.id.smart_support_phone);
        TextView smartEmail = view.findViewById(R.id.smart_support_email);

        smartPhone.setOnClickListener(v -> {
            String phoneNumber = getString(R.string.smart_support_phone);
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(dialIntent);
        });

        smartEmail.setOnClickListener(v -> {
            String emailAddress = getString(R.string.smart_support_email);
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + emailAddress));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Soporte Técnico - Smarts");
            startActivity(emailIntent);
        });

        return view;
    }
}