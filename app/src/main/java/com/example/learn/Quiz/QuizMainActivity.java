package com.example.learn.Quiz;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learn.MainActivity;
import com.example.learn.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
public class QuizMainActivity extends AppCompatActivity {
private TextView QuizTimer;
private RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4;
private TextView opetion1TV,opetion2TV ,opetion3TV,opetion4TV;
private ImageView opetion1Icon,opetion2Icon,opetion3Icon,opetion4Icon;
private TextView TotalquestionTV;
private TextView questionTv;
private int currentQuestionPosition=0;
private int selectedOption=0;
private TextView currentquestion;
private final List<QeustionList> qeustionLists=new ArrayList<>();
private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://learn-de63b-default-rtdb.firebaseio.com/");
   private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        QuizTimer=findViewById(R.id.quizTimer);
        relativeLayout1=findViewById(R.id.opetion1layout);
        relativeLayout2=findViewById(R.id.opetion2layout);
        relativeLayout3=findViewById(R.id.opetion3layout);
        relativeLayout4=findViewById(R.id.opetion4layout);
        questionTv=findViewById(R.id.questionTv);
        opetion1TV=findViewById(R.id.opetion1TV);
        opetion2TV=findViewById(R.id.opetion2TV);
        opetion3TV=findViewById(R.id.opetion3TV);
        opetion4TV=findViewById(R.id.opetion4TV);

        opetion1Icon=findViewById(R.id.opetion1Icon);
        opetion2Icon=findViewById(R.id.opetion2Icon);
        opetion3Icon=findViewById(R.id.opetion3Icon);
        opetion4Icon=findViewById(R.id.opetion4Icon);

        TotalquestionTV=findViewById(R.id.totalquestionTV);
        currentquestion=findViewById(R.id.currentquestionTV);
        final AppCompatButton nextBtn=findViewById(R.id.nextquestionBtn);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        interstitialAd.show(QuizMainActivity.this);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                       Toast.makeText(QuizMainActivity.this,"Failed to load",Toast.LENGTH_SHORT);
                    }
                });
        InstructionsDialog instructionsDialog=new InstructionsDialog(QuizMainActivity.this);
           instructionsDialog.setCancelable(false);
           instructionsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
           instructionsDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final int getQuizTime=Integer.parseInt(snapshot.child("time").getValue(String.class));
                for (DataSnapshot qeustions:snapshot.child("qeustions").getChildren()){
                    String getqeustion=qeustions.child("qeustion").getValue(String.class);
                    String getopetion1=qeustions.child("opetion1").getValue(String.class);
                    String getopetion2=qeustions.child("opetion2").getValue(String.class);
                    String getopetion3=qeustions.child("opetion3").getValue(String.class);
                    String getopetion4=qeustions.child("opetion4").getValue(String.class);
                    int getanswer=Integer.parseInt(qeustions.child("answer").getValue(String.class));
                  QeustionList qeustionList=new QeustionList(getqeustion,getopetion1,getopetion2,getopetion3,getopetion4,getanswer);
                    qeustionLists.add(qeustionList);
                }
                TotalquestionTV.setText("/"+qeustionLists.size());
                startQuizTimer(getQuizTime);
               selectQuestion(currentQuestionPosition);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuizMainActivity.this,"Faild to get data from database",Toast.LENGTH_SHORT).show();
            }
        });
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption=1;
                selectedOption(relativeLayout1,opetion1Icon);

            }
        });
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedOption=2;
                selectedOption(relativeLayout2,opetion2Icon);
            }
        });
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption=3;
                selectedOption(relativeLayout3,opetion3Icon);

            }
        });
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption=4;
                selectedOption(relativeLayout4,opetion4Icon);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOption!=0) {
                    qeustionLists.get(currentQuestionPosition).setUserSelectedanswer(selectedOption);
                    selectedOption=0;
                    currentQuestionPosition++;
                    if(currentQuestionPosition<qeustionLists.size()){
                     selectQuestion(currentQuestionPosition);
                    }
                    else {
                        countDownTimer.cancel();
                        finishQuiz();
                    }
                }

                else{
                    Toast.makeText(QuizMainActivity.this,"pleas select opetion",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void finishQuiz(){
        Intent intent=new Intent(QuizMainActivity.this,QuizResult.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("Questions",(Serializable)qeustionLists);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    private void startQuizTimer(int maxTimeInSecond){
        countDownTimer=new CountDownTimer(maxTimeInSecond*1000,1000) {
            @Override
            public void onTick(long millisUtilFinished) {
        long getHour= TimeUnit.MICROSECONDS.toHours(millisUtilFinished);
                long getMinut= TimeUnit.MICROSECONDS.toMinutes(millisUtilFinished);
                long getSecond= TimeUnit.MICROSECONDS.toSeconds(millisUtilFinished);
                String generatTime=String.format(Locale.getDefault(),"%02d:%02d:%02d",
                        getHour,getMinut-TimeUnit.HOURS.toMinutes(getHour),
                        getSecond-TimeUnit.MINUTES.toSeconds(getMinut));

                QuizTimer.setText(generatTime);
            }

            @Override
            public void onFinish() {
               finishQuiz();
            }
        };
        countDownTimer.start();
    }
    private void selectQuestion(int questionListPosition){
        RestOptions();
        questionTv.setText(qeustionLists.get(questionListPosition).getQeustion());
        opetion1TV.setText(qeustionLists.get(questionListPosition).getOpetion1());
        opetion2TV.setText(qeustionLists.get(questionListPosition).getOpetion2());
        opetion3TV.setText(qeustionLists.get(questionListPosition).getOpetion3());
        opetion4TV.setText(qeustionLists.get(questionListPosition).getOpetion4());
        currentquestion.setText("Question"+(questionListPosition+1));

    }
    private void RestOptions(){
//        relativeLayout1.setBackgroundResource(R.drawable.round_back_white);
//        relativeLayout2.setBackgroundResource(R.drawable.round_back_white);
//        relativeLayout3.setBackgroundResource(R.drawable.round_back_white);
//        relativeLayout4.setBackgroundResource(R.drawable.round_back_white);

        opetion1Icon.setImageResource(R.drawable.round_back);
        opetion2Icon.setImageResource(R.drawable.round_back);
        opetion3Icon.setImageResource(R.drawable.round_back);
        opetion4Icon.setImageResource(R.drawable.round_back);
    }
    private void selectedOption(RelativeLayout selectedOptionLayout,ImageView selectedOptionIcon){
     RestOptions();
     selectedOptionIcon.setImageResource(R.drawable.check_icon);
//     selectedOptionLayout.setBackgroundResource(R.drawable.round_back_selected_opetion);
    }

}