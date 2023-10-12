package com.attitud.ssc.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.attitud.ssc.R;


public class TextrepeaterFragment extends Fragment {

    private String mParam1;
    private String mParam2;


    public TextrepeaterFragment() {
        // Required empty public constructor
    }




        // Inflate the layout for this fragment
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the fragment layout
            View view = inflater.inflate(R.layout.fragment_textrepeater, container, false);




            final EditText inputText = view.findViewById(R.id.editTextInput);
            final EditText repetitionText = view.findViewById(R.id.editTextRepetition);
            Button repeatButton = view.findViewById(R.id.buttonRepeat);
            final TextView resultTextView = view.findViewById(R.id.textViewResult);
            Button copy=view.findViewById(R.id.copy);
            Button share=view.findViewById(R.id.share);


            repeatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String input = inputText.getText().toString();
                    int repetitions = Integer.parseInt(repetitionText.getText().toString());

                    StringBuilder resultBuilder = new StringBuilder();

                    for (int i = 0; i < repetitions; i++) {
                        resultBuilder.append(input);
                        resultBuilder.append("\n");  // Add a newline character after each repetition
                    }

                    resultTextView.setText(resultBuilder.toString());
                }
            });



          copy.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  String textToCopy =  resultTextView.getText().toString();
                  copyToClipboard(textToCopy);


              }



                  private void copyToClipboard(String textToCopy) {
                      ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                      if (clipboardManager != null) {
                          // Create a ClipData object to store the text to copy
                          ClipData clipData = ClipData.newPlainText("Copied Text", textToCopy);

                          // Set the ClipData to clipboard
                          clipboardManager.setPrimaryClip(clipData);

                          Toast.makeText(getContext(), "😊Copied successfully😊", Toast.LENGTH_SHORT).show();

                      }
                  }



          });





            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    shareText();

                }

                private void shareText() {

                    String textToShare = resultTextView.getText().toString();

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                }
            });











            // Find the button by its ID
           /* Button buttonShowToast = view.findViewById(R.id.testbutton);
            Animation pulseAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse_animation);

            // Start the animation
            buttonShowToast.startAnimation(pulseAnimation);

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

