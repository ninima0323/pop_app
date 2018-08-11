package com.example.kimnahyeon.pop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageButton;

import com.example.kimnahyeon.pop.adapter.TaskAdapter;
import com.example.kimnahyeon.pop.data.TaskData;
import com.example.kimnahyeon.pop.for_sql.DBManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnStartDragListener{


    RecyclerView taskView;
    private ArrayList<TaskData> taskList;
    private DBManager dbManager;

    ItemTouchHelper mItemTouchHelper;
    TaskAdapter mAdapter;
    ImageButton setBtn,addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        taskList = dbManager.selectAllTask();
        taskView=(RecyclerView)findViewById(R.id.main_task_view);
        taskView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter=new TaskAdapter(this, taskList, this);

        TaskItemTouchHelperCallback mCallback= new TaskItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(taskView);
        taskView.setAdapter(mAdapter);

        addBtn=(ImageButton)findViewById(R.id.main_add_btn);
        setBtn=(ImageButton)findViewById(R.id.main_set_btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, Manager.RC_ADD);
            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(MainActivity.this, SetActivity.class);
                startActivityForResult(setIntent, Manager.RC_SET);
            }
        });
    }



    @Override
    public void onStartDrag(TaskAdapter.TaskViewHolder holder) {
        mItemTouchHelper.startDrag(holder);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Manager.RC_ADD) {
            if (resultCode == RESULT_OK) {
               taskList = dbManager.selectAllTask();
//                taskView.setAdapter(new TaskAdapter(this, taskList, this));
                mAdapter.setTaskList(taskList);
            }
        }
        else if(requestCode == Manager.RC_SET){
            if(resultCode == RESULT_OK){
                //
            }
        }
    }
}
