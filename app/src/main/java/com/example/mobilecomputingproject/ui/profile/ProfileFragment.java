package com.example.mobilecomputingproject.ui.profile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilecomputingproject.R;

public class ProfileFragment extends Fragment {

    private ImageView imgIcon;
    private TextView tvAppName, tvDescription, tvAuthor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgIcon      = view.findViewById(R.id.imgAppIcon);
        tvAppName    = view.findViewById(R.id.tvAppName);
        tvDescription= view.findViewById(R.id.tvDescription);
        tvAuthor     = view.findViewById(R.id.tvAuthor);

        tvDescription.setText("Browse and play tracks stored locally");
        tvAuthor.setText("Developed by Gary Ken Micallef");

    }
}
