package com.buddy2.darkroom.buddy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
TextView txt;
ImageButton img;
SpeechRecognizer mySpeechRecongnizer;
Intent mySpeechRecongnizerIntent;
ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        mySpeechRecongnizer=SpeechRecognizer.createSpeechRecognizer(this);
        mySpeechRecongnizerIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mySpeechRecongnizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mySpeechRecongnizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        txt=(TextView)findViewById(R.id.txtSpeech);
        txt.setText("Hello Buddy");
        constraintLayout=(ConstraintLayout)findViewById(R.id.background);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String color1[]={

                        "#37FFF4",
                        "#007AFF",
                        "#4EFF6B",
                        "#E1FF00"
                };
                String color2[]={

                        "#FF00E0",
                        "#69009C",
                        "#2534FF",
                        "#FA0404"

                };
                Random random=new Random();
                GradientDrawable gradientDrawable=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                        new int[]{Color.parseColor(color1[random.nextInt(4)]),Color.parseColor(color2[random.nextInt(4)])});

                constraintLayout.setBackground(gradientDrawable);
                return false;
            }
        });
        mySpeechRecongnizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> matches=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches!=null){
                    txt.setText(matches.get(0));
                }
                else
                    txt.setText("Sorry Buddy! I can't understand");
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        img=(ImageButton)findViewById(R.id.imgBtn);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        img.setImageResource(R.drawable.out_ico);
                        mySpeechRecongnizer.stopListening();

                        break;
                    case MotionEvent.ACTION_DOWN:
                        txt.setText("");
                        txt.setText("Listening....");
                        img.setImageResource(R.drawable.in_ico);
                        mySpeechRecongnizer.startListening(mySpeechRecongnizerIntent);
                        break;

                }
                return false;
            }
        });
    }
    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED)){
                Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+ getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
}
