package com.example.learn.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.List;

public class QuizResult extends AppCompatActivity {
private List<QeustionList>qeustionLists =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        final TextView score=findViewById(R.id.scoreTv);
         final TextView totalscore=findViewById(R.id.totalScoreTv);
         final  TextView correctTv=findViewById(R.id.correctTv);
         final TextView IncorrectTv=findViewById(R.id.incorrectTv);
        final AppCompatButton sharedbtn=findViewById(R.id.shareBtn);
        final AppCompatButton retakeBtn=findViewById(R.id.retakequiz);
        qeustionLists=(List<QeustionList>) getIntent().getSerializableExtra("Questions");
        totalscore.setText("/"+qeustionLists.size());
        score.setText(getCorrectAnswer()+"");
        correctTv.setText(getCorrectAnswer()+"");
        IncorrectTv.setText(String.valueOf(qeustionLists.size()-getCorrectAnswer()));

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        interstitialAd.show(QuizResult.this);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Toast.makeText(QuizResult.this,"Failed to load",Toast.LENGTH_SHORT);
                    }
                });

        sharedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"MY Score "+score.getText());
                Intent ShareIntent= Intent.createChooser(sendIntent, "Share va");
                startActivity(ShareIntent);

            }
        });
        retakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizResult.this,QuizMainActivity.class));
                finish();
            }
        });

    }
    private int getCorrectAnswer(){
        int correctAnswer=0;
        for (int i=0;i<qeustionLists.size();i++){
           int getUserSelecedOpetion=qeustionLists.get(i).getUserSelectedanswer();
           int getQuestionAnswer=qeustionLists.get(i).getAnswer();
           if (getQuestionAnswer==getUserSelecedOpetion){
               correctAnswer++;

           }
        }
        return correctAnswer;
    }
}