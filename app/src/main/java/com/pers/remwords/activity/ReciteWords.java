package com.pers.remwords.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pers.remwords.R;
import com.pers.remwords.adapter.WordsAdapter;
import com.pers.remwords.dao.WordsDao;
import com.pers.remwords.entity.Data;
import com.pers.remwords.entity.Words;

import java.util.ArrayList;
import java.util.List;

public class ReciteWords extends AppCompatActivity {
    private List<Words> wordsList = new ArrayList<>();
    private WordsAdapter wordsAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Data allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite_words);
        allData = (Data) getApplication();
        initview();
        setSupportActionBar(toolbar);
        initwords();
    }

    private void initview() {
        recyclerView = (RecyclerView) findViewById(R.id.rewordsView);
        toolbar = (Toolbar) findViewById(R.id.toolbar4);
    }

    private void initwords() {
        wordsList.clear();
        WordsDao wordsDao = new WordsDao(ReciteWords.this);
        wordsList = wordsDao.selectReDb(allData.getUanme());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        wordsAdapter = new WordsAdapter(wordsList, this);
        recyclerView.setAdapter(wordsAdapter);
        wordsAdapter.setOnItemClickListener(new WordsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, Words words) {
                Toast.makeText(getBaseContext(), "点击的是" + position + "的itemClick", Toast.LENGTH_SHORT).show();
            }
        });
        //长按事件
        wordsAdapter.setOnItemLongClickListener(new WordsAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, final int position, final Words words) {
                Toast.makeText(getBaseContext(), "点击的是" + position + "的itemClick", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
