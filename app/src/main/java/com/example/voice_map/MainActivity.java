package com.example.voice_map;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //CONSTANT SO CAPITAL
    private static final int SPEAK_REQUEST = 10;

    TextView txt_value;
    Button btn_voice_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_value=(TextView) findViewById(R.id.textValue);
        btn_voice_intent= (Button) findViewById(R.id.btnvoiceintent);
        btn_voice_intent.setOnClickListener(MainActivity.this);
        PackageManager packageManager = this.getPackageManager();
        List<ResolveInfo> listofinformation = packageManager.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0
        );

        if(listofinformation.size() > 0)
        {
            Toast.makeText(MainActivity.this, "Speech recogination is supported", Toast.LENGTH_SHORT).show();
            listentotheuservoice();
        }
        else
        {
            Toast.makeText(MainActivity.this, "Speech recogination is not supported", Toast.LENGTH_SHORT).show();

        }

    }

    private void listentotheuservoice()
    {
        Intent voiceintent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceintent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Talk to me");
        voiceintent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voiceintent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10);
        startActivityForResult(voiceintent,SPEAK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEAK_REQUEST && resultCode == RESULT_OK) {
            ArrayList<String> voicewords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            float[] confidLevels = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

            int index = 0;
            for (String userword : voicewords) {
                if (confidLevels != null && index < confidLevels.length) {

                    txt_value.setText(userword + "-" + confidLevels[index]);
                }
            }

        }
    }
    @Override
    public void onClick(View view)
    {
        listentotheuservoice();

    }
}

