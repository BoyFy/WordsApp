package com.pers.remwords.savename;

import android.content.Context;

import com.pers.remwords.entity.Users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileSave {
    public static boolean saveUserInfo(Context context,String uname,String psd){
        try {
            FileOutputStream output=context.openFileOutput("data.txt",Context.MODE_PRIVATE);
            BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(output));
            writer.write(uname+":"+psd);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Users loadUsers(Context context){
        String[] splitUserInfo=null;
        Users users=null;
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try {
            in=context.openFileInput("data.txt");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                content.append(line);
            }
            splitUserInfo=content.toString().split(":");
            users=new Users(splitUserInfo[0],splitUserInfo[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
}
