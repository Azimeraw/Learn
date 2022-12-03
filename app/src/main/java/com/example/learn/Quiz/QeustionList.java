package com.example.learn.Quiz;

import java.io.Serializable;

public class QeustionList implements Serializable {


    private final String qeustion,opetion1,opetion2,opetion3,opetion4;
    private final int answer;
    private int userSelectedanswer;
    public QeustionList(String qeustion, String opetion1, String opetion2, String opetion3, String opetion4, int answer) {
        this.qeustion = qeustion;
        this.opetion1 = opetion1;
        this.opetion2 = opetion2;
        this.opetion3 = opetion3;
        this.opetion4 = opetion4;
        this.answer = answer;
        userSelectedanswer=0;
    }

    public String getQeustion() {
        return qeustion;
    }

    public String getOpetion1() {
        return opetion1;
    }

    public String getOpetion2() {
        return opetion2;
    }

    public String getOpetion3() {
        return opetion3;
    }

    public String getOpetion4() {
        return opetion4;
    }

    public int getAnswer() {
        return answer;
    }

    public int getUserSelectedanswer() {
        return userSelectedanswer;
    }

    public void setUserSelectedanswer(int userSelectedanswer) {
        this.userSelectedanswer = userSelectedanswer;
    }
}
