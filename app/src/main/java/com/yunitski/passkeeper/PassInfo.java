package com.yunitski.passkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PassInfo extends AppCompatActivity implements View.OnClickListener {

    TextView res, link, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_info);

        res = findViewById(R.id.tvResInfo);
        link = findViewById(R.id.tvLinkInfo);
        pass = findViewById(R.id.tvPassInfo);

        Intent intent = getIntent();
        String r = intent.getStringExtra("resIn");
        String l = intent.getStringExtra("linkIn");
        String p = intent.getStringExtra("passIn");
        res.setText(r);
        link.setText(l);
        pass.setText(p);
        res.setClickable(true);
        link.setClickable(true);
        pass.setClickable(true);
        res.setOnClickListener(this);
        link.setOnClickListener(this);
        pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvResInfo:
                String s = res.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied_text", s);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Скопировано", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvLinkInfo:
                String s2 = link.getText().toString();
                ClipboardManager clipboard2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip2 = ClipData.newPlainText("copied_text", s2);
                clipboard2.setPrimaryClip(clip2);
                Toast.makeText(this, "Скопировано", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvPassInfo:
                String s3 = pass.getText().toString();
                ClipboardManager clipboard3 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip3 = ClipData.newPlainText("copied_text", s3);
                clipboard3.setPrimaryClip(clip3);
                Toast.makeText(this, "Скопировано", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}