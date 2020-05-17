package com.pers.remwords.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.pers.remwords.R;
import com.pers.remwords.activity.index;
import com.pers.remwords.base.BaseActivity;
import com.pers.remwords.dao.WordsDao;
import com.pers.remwords.entity.Users;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Button login;
    private Button register;
    private EditText uname;
    private EditText psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    private void initview(){
        login=(Button)findViewById(R.id.login);
        register=(Button)findViewById(R.id.register);
        uname=(EditText)findViewById(R.id.uname);
        psd=(EditText)findViewById(R.id.psd);
    }

    private Boolean checkUsers() {
        String username = uname.getText().toString();
        String password = psd.getText().toString();
        WordsDao wordsDao = new WordsDao(this);
        try {
            if ("".equals(username) || "".equals(password)) {
                Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_error)
                        .setTitle("登录失败")
                        .setMessage("用户名或密码不能为空")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
                ((AlertDialog) dialog).getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                ((AlertDialog) dialog).getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            } else {
                Boolean flag = wordsDao.selectUsersDb(username, password);
                if (flag) {
                    return true;
                } else {
                    Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setIcon(R.drawable.ic_error)
                            .setTitle("登录失败")
                            .setMessage("用户名或密码错误")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create();
                    dialog.show();
                    ((AlertDialog) dialog).getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    ((AlertDialog) dialog).getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if (checkUsers()) {
                    Intent intent = new Intent(this, index.class);
                    intent.putExtra("uanme", uname.getText().toString());
                    startActivity(intent);
                }
                break;
            case R.id.register:
                Log.d("TAG", "onClick: ");
                Intent intent1=new Intent(this,RegistActivity.class);
                startActivity(intent1);
                break;
            default:
        }
    }
}
