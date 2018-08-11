package com.example.kimnahyeon.pop;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kimnahyeon.pop.data.TaskData;
import com.example.kimnahyeon.pop.for_sql.DBManager;

public class EditActivity extends AppCompatActivity {

    TextView title;
    EditText etContent;
    Button saveBtn;

    private TaskData task;
    private int tid = -1;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbManager = new DBManager(this);

        title=(TextView)findViewById(R.id.edit_title);
        etContent=(EditText)findViewById(R.id.edit_content);
        saveBtn=(Button)findViewById(R.id.edit_save_btn);

        tid = getIntent().getIntExtra("tid", -1);

        if (tid > 0) {
            task = dbManager.selectTask(tid);
            setEditValue();
        } else {
            task = new TaskData();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                if (tid > 0)
                    dbManager.updateTask(task);
                else
                    dbManager.insertTask(task);

                setResult(RESULT_OK);
                finish();

            }
        });
    }

    private void setEditValue() {
        title.setText("할 일을 수정하세요");

        etContent.setText(task.getContent());
    }

    private void saveData() {
        task.setContent(etContent.getText().length() == 0 ? null : etContent.getText().toString());
    }

    public void onBackPressed() {
        saveData();

        if (tid > 0 && !task.equals(dbManager.selectTask(tid))) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);

            dialog.setCancelable(true);
            dialog.setMessage("작성하던 내용이 삭제됩니다.");

            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    EditActivity.super.onBackPressed();
                }
            });

            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } else {
            super.onBackPressed();
        }
    }

}
