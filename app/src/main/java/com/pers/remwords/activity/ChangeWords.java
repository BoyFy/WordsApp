package com.pers.remwords.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pers.remwords.R;

public class ChangeWords extends AppCompatActivity {

    private EditText iptWord;
    private EditText iptmean;
    private Button btnensure;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_words);
        initview();
        String word=getIntent().getStringExtra("changeword");
        String mean=getIntent().getStringExtra("changemean");
        iptWord.setText(word);
        iptmean.setText(mean);
        setSupportActionBar(toolbar);
        btnensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("changeword",iptWord.getText().toString());
                intent.putExtra("changemeans",iptmean.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void initview(){
        iptWord=(EditText)findViewById(R.id.change_word);
        iptmean=(EditText)findViewById(R.id.change_means);
        btnensure=(Button)findViewById(R.id.changebtn_ensure);
        toolbar=(Toolbar)findViewById(R.id.toolbar2);
    }
}
