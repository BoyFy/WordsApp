package com.pers.remwords.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pers.remwords.R;
import com.pers.remwords.adapter.WordsAdapter;
import com.pers.remwords.base.ActivityCollect;
import com.pers.remwords.base.BaseActivity;
import com.pers.remwords.dao.WordsDao;
import com.pers.remwords.entity.Data;
import com.pers.remwords.entity.Words;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class index extends BaseActivity{
    private List<Words> wordsList = new ArrayList<>();
    private WordsAdapter wordsAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;
    private Data allData;
    private FloatingActionButton fab;
    private TextView lastTime;
    private TextView nav_uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //获取从登录界面传递来的用户名
        allData=(Data)getApplication();
        String uname=getIntent().getStringExtra("uanme");
        allData.setUanme(uname);
        //对控件进行注册
        initView();

        //从time文件中获取上次登录时间
        SharedPreferences time = getSharedPreferences(uname+"time", Context.MODE_PRIVATE);
        String lasttime=time.getString("date","第一次登陆");
        Log.d("TAG", "onCreate: "+lasttime);
        lastTime.setText(lasttime);
        nav_uname.setText("账号:"+uname);
        //把登录时间保存在time文件下
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        SharedPreferences.Editor editor=getSharedPreferences(uname+"time", Context.MODE_PRIVATE).edit();
        editor.putString("date",simpleDateFormat.format(date));
        editor.apply();

        //添加toobar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        //添加点击显示Nav菜单
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.recite:
                        Intent intent=new Intent(index.this,ReciteWords.class);
                        startActivity(intent);
                        break;
                    case R.id.aboutme:
                        Toast.makeText(index.this,"什么也没有啊，不要再点了。",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.finishing:
                        ActivityCollect.finishAll();
                        break;
                    default:
                }
                return false;
            }
        });

        //FloatingActionButton的点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(index.this, AddWords.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    @SuppressLint("ResourceType")
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.wordsView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navView = (NavigationView) findViewById(R.id.navView);
        View header = navView.getHeaderView(0);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        lastTime=(TextView) header.findViewById(R.id.time);
        nav_uname=(TextView)header.findViewById(R.id.nav_uname);
    }

    //初始化RecycleView的列表单词，并且添加监听事件
    private void initWords() {
        wordsList.clear();
        WordsDao wordsDao = new WordsDao(index.this);
        wordsList = wordsDao.selectDb(allData.getUanme());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        wordsAdapter = new WordsAdapter(wordsList, this);
        recyclerView.setAdapter(wordsAdapter);
        //点击事件
        wordsAdapter.setOnItemClickListener(new WordsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position,Words words) {
                Intent intent=new Intent(index.this,ShowWords.class);
                intent.putExtra("showword",words.getWord());
                intent.putExtra("showmean",words.getMeans());
                startActivity(intent);
                Toast.makeText(getBaseContext(), "点击的是" + position + "的itemClick", Toast.LENGTH_SHORT).show();
            }
        });
        //长按事件
        wordsAdapter.setOnItemLongClickListener(new WordsAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View view, final int position, final Words words) {
                PopupMenu popupMenu=new PopupMenu(index.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.longclick_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.change:
                                Intent intent = new Intent(index.this, ChangeWords.class);
                                intent.putExtra("changeword",words.getWord());
                                intent.putExtra("changemean",words.getMeans());
                                startActivityForResult(intent, 2);
                                break;
                            case R.id.delete:
                                wordsList.remove(position);
                                WordsDao wordsDao = new WordsDao(index.this);
                                wordsDao.delectDb(words,allData.getUanme());
                                Toast.makeText(getBaseContext(),""+position,Toast.LENGTH_SHORT).show();
                                wordsAdapter.notifyDataSetChanged();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDrawerLayout.closeDrawers();
        initWords();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDrawerLayout.closeDrawers();
        if (data != null) {
            if (requestCode == 1) {
                Log.d("TAG", data.toString());
                if (resultCode == RESULT_OK) {
                    String word = data.getStringExtra("addword");
                    String means = data.getStringExtra("addmeans");
                    Words words = new Words(word, means);
                    WordsDao wordsDao = new WordsDao(index.this);
                    wordsDao.insertDb(words,allData.getUanme());
                }
            }
            if (requestCode == 2) {
                if (resultCode == RESULT_OK) {
                    String word = data.getStringExtra("changeword");
                    String means = data.getStringExtra("changemeans");
                    Words words = new Words(word, means);
                    WordsDao wordsDao = new WordsDao(index.this);
                    wordsDao.changeDb(words,allData.getUanme());
                }
            }

        }
    }

}
