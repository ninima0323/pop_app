package com.example.kimnahyeon.pop.data;

/**
 * Created by kimnahyeon on 2017. 7. 23..
 */

public class TaskData {
    private int tid;
    private String content;
    private Boolean isDone = false;
    int indexno=0;

    public TaskData(){}

    public TaskData(int tid, int index, String content, Boolean isDone ){
        this.tid = tid;
        this.indexno =index;
        this.content = content;
        this.isDone = isDone;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public int getIndexno() {
        return indexno;
    }

    public void setIndexno(int indexno) {
        this.indexno = indexno;
    }
}
