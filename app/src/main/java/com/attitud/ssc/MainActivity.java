package com.attitud.ssc;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.attitud.ssc.adapters.ViewPagerAdapter;
import com.attitud.ssc.databinding.ActivityMainBinding;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;


public class MainActivity extends AppCompatActivity {

   private ActivityMainBinding binding;

   private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       viewPagerAdapter = new ViewPagerAdapter(this);
       binding.viewPager.setAdapter(viewPagerAdapter);
        

        binding.topNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
          @Override
          public void onNavigationChanged(View view, int position) {
              binding.viewPager.setCurrentItem(position,true);
          }
      });

      binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageSelected(int position) {
              super.onPageSelected(position);
              binding.topNavigation.setCurrentActiveItem(position);
          }
      });
    }
}