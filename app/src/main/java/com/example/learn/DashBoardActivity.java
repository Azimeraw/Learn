package com.example.learn;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.learn.Quiz.QuizMainActivity;
import com.example.learn.Recycler_veiw.StudentRecycler_MainActivity;

public class DashBoardActivity extends Activity {
    private Button logout;
    TextView buttonone,buttontwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        buttonone=findViewById(R.id.student);
        buttontwo=findViewById(R.id.quiz_menuid);
        buttonone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DashBoardActivity.this, QuizMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttontwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DashBoardActivity.this, StudentRecycler_MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
}}
