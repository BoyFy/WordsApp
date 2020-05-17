package com.pers.remwords.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pers.remwords.R;
import com.pers.remwords.dao.WordsDao;
import com.pers.remwords.entity.Data;
import com.pers.remwords.entity.Words;

public class ShowWords extends AppCompatActivity {

    private TextView showword;
    private TextView showmean;
    private Button remb_btn;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_words);
        initview();
        setSupportActionBar(toolbar);
        String word = getIntent().getStringExtra("showword");
        String mean = getIntent().getStringExtra("showmean");
        showword.setText(word);
        showmean.setText(mean);
        final Words words=new Words(word,mean);
        final Data allData = (Data) getApplication();
        remb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordsDao wordsDao = new WordsDao(ShowWords.this);
                wordsDao.delectDb(words,allData.getUanme());
                wordsDao.insertReDb(words,allData.getUanme());
                finish();
            }
        });
    }

    private void initview(){
    showword=(TextView)findViewById(R.id.showword);
    showmean=(TextView)findViewById(R.id.showmean);
    remb_btn=(Button)findViewById(R.id.remb_btn);
    toolbar=(Toolbar)findViewById(R.id.toolbar3);
    }
}
