package com.example.learn.Quiz;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.learn.R;

public class InstructionsDialog extends Dialog {
    private int instructionpoints=0;
    public InstructionsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_dialog);
        final AppCompatButton continueBtn=findViewById(R.id.continueBtn);
        TextView instructionsTv=findViewById(R.id.instructionTv);
        setInstructionPoint(instructionsTv,"1. you will get maximum 2 minutes to complet the quiz.");
        setInstructionPoint(instructionsTv,"2. you will get 1 point every 1 correct answer");

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             dismiss();
            }
        });
    }
    private void setInstructionPoint(TextView instructionTv,String instructionPoint){
if (instructionpoints==0){
    instructionTv.setText(instructionPoint);
}
else {
instructionTv.setText(instructionTv.getText()+"\n\n"+instructionPoint);
}
    }
}
