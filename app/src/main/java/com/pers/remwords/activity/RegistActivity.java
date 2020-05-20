package com.pers.remwords.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pers.remwords.R;
import com.pers.remwords.base.BaseActivity;
import com.pers.remwords.dao.WordsDao;
import com.pers.remwords.entity.Users;

public class RegistActivity extends BaseActivity implements View.OnClickListener {
    private Button ensurebtn;
    private EditText uname;
    private EditText psd;
    private EditText psdEnsure;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        setSupportActionBar(toolbar);
        ensurebtn.setOnClickListener(this);
    }

    private void initView() {
        ensurebtn = (Button) findViewById(R.id.ensurebtn_rgst);
        uname = (EditText) findViewById(R.id.uname_rgst);
        psd = (EditText) findViewById(R.id.psd_rgst);
        psdEnsure = (EditText) findViewById(R.id.psd_ensure);
        toolbar = (Toolbar) findViewById(R.id.toobar_rgst);
    }

    @Override
    public void onClick(View v) {
        String username = uname.getText().toString();
        String password = psd.getText().toString();
        String passwordEnsure = psdEnsure.getText().toString();
        WordsDao wordsDao = new WordsDao(this);
        try {
            if ("".equals(username) || "".equals(passwordEnsure) || "".equals(password)) {
                Dialog dialog = new AlertDialog.Builder(RegistActivity.this)
                        .setIcon(R.drawable.ic_error)
                        .setTitle("注册失败")
                        .setMessage("用户名密码等不能为空")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
                ((AlertDialog) dialog).getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                ((AlertDialog) dialog).getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            } else if (!password.equals(passwordEnsure)) {
                Dialog dialog = new AlertDialog.Builder(RegistActivity.this)
                        .setIcon(R.drawable.ic_error)
                        .setTitle("注册失败")
                        .setMessage("两次密码输如不一样")
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
                Boolean flag=wordsDao.selectUsesUname(username);
                if (flag) {
                    Users users = new Users(username, password);
                    wordsDao.insertUSersDb(users);
                    Dialog dialog = new AlertDialog.Builder(RegistActivity.this)
                            .setIcon(R.drawable.ic_success)
                            .setTitle("注册成功")
                            .setMessage("点击确定将返回登录界面")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .create();
                    dialog.show();
                    ((AlertDialog) dialog).getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    ((AlertDialog) dialog).getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }else {
                    Dialog dialog = new AlertDialog.Builder(RegistActivity.this)
                            .setIcon(R.drawable.ic_error)
                            .setTitle("注册失败")
                            .setMessage("用户名已存在")
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

    }
}
