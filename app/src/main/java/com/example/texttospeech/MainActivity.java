package com.example.texttospeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private EditText mEditText;
    private Button mSpeechBtn;
    private static final int TTS_ENGINE_REQUEST = 101;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.edit_text);
        mSpeechBtn = findViewById(R.id.speech_btn);


        mSpeechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
                startActivityForResult(intent,TTS_ENGINE_REQUEST);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TTS_ENGINE_REQUEST && resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {

            textToSpeech = new TextToSpeech(this, this);

        } else {

            Intent install = new Intent();
            install.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
            startActivity(install);

        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){

            int languageStatus = textToSpeech.setLanguage(Locale.ENGLISH);
            if (languageStatus == TextToSpeech.LANG_MISSING_DATA || languageStatus == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Language Not Supported...", Toast.LENGTH_SHORT).show();
            }
            else {
                String data = mEditText.getText().toString();
                int spechStatus = textToSpeech.speak(data,TextToSpeech.QUEUE_FLUSH,null);
                if (spechStatus == TextToSpeech.ERROR){
                    Toast.makeText(this, "Error While Speech...", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else{


            Toast.makeText(this, "Text To Speech Engine Failed...", Toast.LENGTH_SHORT).show();
        }
    }
}
