package com.attitud.ssc.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.attitud.ssc.R;
import com.attitud.ssc.cls.MySharePreference;
import com.attitud.ssc.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

private FragmentSettingBinding binding;

    public SettingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      binding = FragmentSettingBinding.inflate(inflater,container,false);
      return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateDarkModeButton();

        binding.nightModeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MySharePreference.getInstance(getContext()).saveDarkMode(isChecked);

                if (isChecked){
                    // Change to dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else {
                    // Change to light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        binding.shareContainer.setOnClickListener(v -> {
            String appUrl = "https://play.google.com/store/apps/details?id=" + "com.attitude.ssc";

            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");

            intent.putExtra(android.content.Intent.EXTRA_TEXT,appUrl);

            startActivity(Intent.createChooser(intent, getString(R.string.share_using)));

        });

        binding.reviewContainer.setOnClickListener(v -> {
            String appUrl = "https://play.google.com/store/apps/details?id=" + "com.attitude.ssc";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(appUrl));
            startActivity(i);
        });

        binding.privacyContainer.setOnClickListener(v -> {
            String appUrl = "https://www.privacypolicygenerator.info/live.php?token=7qVTsRr7AmFxhmIaZ0X64eP1hlVuBq8E";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(appUrl));
            startActivity(i);
        });



        binding.directtalk.setOnClickListener(v -> {
            String appUrl = "https://api.whatsapp.com/send?phone=917908140109";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(appUrl));
            startActivity(i);
        });



        binding.bugContainer.setOnClickListener(v -> {
            final Intent intent = new Intent(Intent.ACTION_SENDTO)
                    .setType("plain/text")
                    . setData(Uri.parse("mailto:abhijitmalik40@gmail.com"))
                    .putExtra(Intent.EXTRA_SUBJECT, "Bug report")
                    .putExtra(Intent.EXTRA_TEXT, "Hello ,Tell me about the Bug :-)");
            startActivity(intent);
        });

        binding.requestFeatureContainer.setOnClickListener(v -> {
            final Intent intent = new Intent(Intent.ACTION_SENDTO)
                    .setType("plain/text")
                    . setData(Uri.parse("mailto:abhijitmalik40@gmail.com"))
                    .putExtra(Intent.EXTRA_SUBJECT, "Request New feature")
                    .putExtra(Intent.EXTRA_TEXT, "Request New feature :-)");
            startActivity(intent);
        });


    }


    private void  updateDarkModeButton(){
//        int nightModeFlags =
//                getContext().getResources().getConfiguration().uiMode &
//                        Configuration.UI_MODE_NIGHT_MASK;
//        switch (nightModeFlags) {
//            case Configuration.UI_MODE_NIGHT_YES:
//           binding.nightModeButton.setChecked(true);
//                break;
//
//            case Configuration.UI_MODE_NIGHT_NO:
//      binding.nightModeButton.setChecked(false);
//                break;
//        }

        boolean isDarkMode = MySharePreference.getInstance(getContext()).isDarkMode();
        binding.nightModeButton.setChecked(isDarkMode);

    }
}