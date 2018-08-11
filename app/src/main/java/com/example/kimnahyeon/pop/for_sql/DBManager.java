package com.example.kimnahyeon.pop.for_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kimnahyeon.pop.Manager;
import com.example.kimnahyeon.pop.data.TaskData;

import java.util.ArrayList;

/**
 * Created by kimnahyeon on 2017. 7. 23..
 */

public class DBManager {
    private Context context;
    private MySQLOpenHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        this.context = context;
        helper = new MySQLOpenHelper(context, "eos_task.db", null, 1);
        db = helper.getWritableDatabase(); // 읽고 쓸 수 있는 Database 셋팅
    }

    /** Contact **/
    public ArrayList<TaskData> selectAllTask() {
        ArrayList<TaskData> taskList = new ArrayList<>();

        Cursor c = db.query("task", null, null, null, null, null, "tid");
        while (c.moveToNext()) {
            taskList.add(new TaskData(c.getInt(c.getColumnIndex("tid")),c.getInt(c.getColumnIndex("tid")), c.getString(c.getColumnIndex("content")), c.getInt(c.getColumnIndex("isdone")) > 0));
        }

        return taskList;
    }

    public TaskData selectTask(int tid) {
        if (tid <= 0)
            return new TaskData();

        Cursor c = db.query("task", null, "tid = ?", new String[]{tid + ""}, null, null, null);

        if (c.moveToNext()) {
            return new TaskData(tid, tid,c.getString(c.getColumnIndex("content")), c.getInt(c.getColumnIndex("isdone")) > 0);
        } else {
            return new TaskData();
        }
    }

    public void insertTask(TaskData task) {
        ContentValues values = new ContentValues();

        values.put("content", task.getContent());
        values.put("isdone", false);

        db.insert("task", null, values);
    }

    public void updateTask(TaskData task) {
        ContentValues values = new ContentValues();

        values.put("content", task.getContent());
        values.put("isdone", task.getDone());

        db.update("task", values, "tid=?", new String[]{task.getTid() + ""});
    }


    public void deleteTask(int tid) {
        db.delete("task", "tid = ?", new String[]{tid + ""});
    }

    //설정화면 위한 디비 구현
    public void updateSettings(Manager manager){
        ContentValues values = new ContentValues();

        values.put("isalways", manager.isAlways());
        values.put("back_image", manager.getBackImg());

        db.update("bob", values,"sid=?",new String[]{manager.getSid() + ""});
    }

    public boolean getIsAlways(){
        Cursor c = db.query("bob", null, "sid = ?", new String[]{Manager.getSid() + ""}, null, null, null);
        Integer b = c.getInt(c.getColumnIndex("isalways"));
        if(b==0) return false;
        else return true;
    }

    public void insertSettings(Manager manager){
        ContentValues values = new ContentValues();

        values.put("isalways", true);
        values.put("back_image", "");

        db.insert("bob", null, values);
        Log.e("insert", "worked");
    }


}
