package com.attitud.ssc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.attitud.ssc.databinding.ActivityCatetoryBinding;

import com.attitud.ssc.fragments.HomeFragment;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCatetoryBinding binding;
    private String catId;


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatetoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setup();

    }


    private void setup() {
        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        Intent intent = getIntent();
        catId = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        if (name != null) {
            getSupportActionBar().setTitle(name);
        }
        if (catId != null) {
            showFragment(new HomeFragment(catId));

        }


    }

    private void showFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        getSupportFragmentManager().beginTransaction().replace(binding.fragmentContainer.getId(), fragment)
                .commit();
    }
}