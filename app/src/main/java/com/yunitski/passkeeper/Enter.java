package com.yunitski.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Enter extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences preferences;
    Button one, two, three, four, five, six, seven, eight, nine, zero, del;
    String enterCode, in1, in2, in3, in4, inputCode;
    int pass;
    ImageView first, second, third, fourth;
    boolean is1en, is2en, is3en, is4en;
    PassCode passCode;
    TextView greet;
    public static final String PASS_C = "ACCESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        del = findViewById(R.id.del);
        first = findViewById(R.id.firstC);
        second = findViewById(R.id.secC);
        third = findViewById(R.id.thC);
        fourth = findViewById(R.id.foC);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        del.setOnClickListener(this);
        greet = findViewById(R.id.greet);
        enterCode = "";
        inputCode = "";
        in1 = "";
        in2 = "";
        in3 = "";
        in4 = "";
        is1en = false;
        is2en = false;
        is3en = false;
        is4en = false;
        preferences = getSharedPreferences(PASS_C, MODE_PRIVATE);
            if (preferences.contains(PASS_C)){
                greet.setText("Введите пароль");
                pass = preferences.getInt(PASS_C, 0);
            } else if (pass == 0){
                greet.setText("Придумайте пароль");
                checkIt();
            }

    }

    private void firstEnter(){
        if (pass == 0){
            String t = in1 + in2 + in3 + in4;
            pass = Integer.parseInt(t);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(PASS_C, pass);
            editor.apply();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                checkInput(1);
                break;
            case R.id.two:
                checkInput(2);
                break;
            case R.id.three:
                checkInput(3);
                break;
            case R.id.four:
                checkInput(4);
                break;
            case R.id.five:
                checkInput(5);
                break;
            case R.id.six:
                checkInput(6);
                break;
            case R.id.seven:
                checkInput(7);
                break;
            case R.id.eight:
                checkInput(8);
                break;
            case R.id.nine:
                checkInput(9);
                break;
            case R.id.zero:
                checkInput(0);
                break;
            case R.id.del:
                setDel();
                break;
        }

    }
    private void checkInput(int num){
        if (enterCode.length() <4) {
            enterCode += num;
            if (!is1en && !is2en && !is3en && !is4en) {
                first.setImageResource(R.drawable.circle_half_fill);
                is1en = true;
                in1 = ""+num;
            } else if (is1en && !is2en && !is3en && !is4en) {
                second.setImageResource(R.drawable.circle_half_fill);
                is2en = true;
                in2 = ""+num;
            } else if (is1en && is2en && !is3en && !is4en) {
                third.setImageResource(R.drawable.circle_half_fill);
                is3en = true;
                in3 = ""+num;
            } else if (is1en && is2en && is3en && !is4en) {
                fourth.setImageResource(R.drawable.circle_half_fill);
                is4en = true;
                in4 = ""+num;
            }
            checkIt();
        }
    }
    private void setDel(){
        if (is4en && is3en && is2en && is1en){
            inputCode = in1 + in2 + in3;
            enterCode = inputCode;
            is4en = false;
            fourth.setImageResource(R.drawable.circle);
        } else if (!is4en && is3en && is2en && is1en){
            inputCode = in1 + in2;
            enterCode = inputCode;
            is3en = false;
            third.setImageResource(R.drawable.circle);
        } else if (!is4en && !is3en && is2en && is1en){
            inputCode = in1;
            enterCode = inputCode;
            is2en = false;
            second.setImageResource(R.drawable.circle);
        } else if (!is4en && !is3en && !is2en && is1en){
            enterCode = "";
            is1en = false;
            first.setImageResource(R.drawable.circle);
        }
    }
    public void checkIt(){
        if (enterCode.length() == 4) {

            firstEnter();
            if (Integer.parseInt(enterCode) == pass) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                shakeAnim();
                    enterCode = "";
                    inputCode = "";
                    in1 = "";
                    in2 = "";
                    in3 = "";
                    in4 = "";
                    is1en = false;
                    is2en = false;
                    is3en = false;
                    is4en = false;
                    first.setImageResource(R.drawable.circle);
                    second.setImageResource(R.drawable.circle);
                    third.setImageResource(R.drawable.circle);
                    fourth.setImageResource(R.drawable.circle);
            }
        }
    }
    private void shakeAnim(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        findViewById(R.id.linearLayout3).startAnimation(animation);
    }
}