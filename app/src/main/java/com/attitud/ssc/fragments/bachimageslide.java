package com.attitud.ssc.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.attitud.ssc.R;


public class bachimageslide extends Fragment {

    private String mParam1;
    private String mParam2;


    public bachimageslide() {
        // Required empty public constructor
    }




        // Inflate the layout for this fragment
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the fragment layout
            View view = inflater.inflate(R.layout.quotes_list, container, false);

            // Find the button by its ID
            /*Button buttonShowToast = view.findViewById(R.id.newtestbutton);

            // Set a click listener for the button
            buttonShowToast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Display a toast message
                    Toast.makeText(getActivity(), "HI", Toast.LENGTH_SHORT).show();
                }
            });*/

            return view;
        }


}

