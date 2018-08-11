package com.example.kimnahyeon.pop;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by kimnahyeon on 2017. 7. 31..
 */

public class Manager {
    public final static  int RC_ADD=1234;
    public final static  int RC_SET=2345;


    //설정화면 데이터베이스
    public static String backImg;
    public static boolean isAlways=true;
    public static int sid=0;

    Manager(){
    }

    public static String getBackImg() {
        return backImg;
    }

    public static void setBackImg(String backImg) {
        Manager.backImg = backImg;
    }

    public static boolean isAlways() {
        return isAlways;
    }

    public static void setIsAlways(boolean isAlways) {
        Manager.isAlways = isAlways;
    }

    public static int getSid() {
        return sid;
    }

    public static void setSid(int sid) {
        Manager.sid = sid;
    }
}
