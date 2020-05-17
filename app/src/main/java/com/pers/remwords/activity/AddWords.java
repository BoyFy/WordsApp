package com.pers.remwords.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

import com.pers.remwords.R;
import com.pers.remwords.base.BaseActivity;

public class AddWords extends BaseActivity {
    private EditText iptWord;
    private EditText iptmean;
    private Button btnensure;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_words);
        initview();

        setSupportActionBar(toolbar);
        btnensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("addword",iptWord.getText().toString());
                intent.putExtra("addmeans",iptmean.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }


    private void initview(){
        iptWord=(EditText)findViewById(R.id.input_word);
        iptmean=(EditText)findViewById(R.id.input_means);
        btnensure=(Button)findViewById(R.id.changebtn_ensure);
        toolbar=(Toolbar)findViewById(R.id.toolbar1);
    }
}
