package com.example.kimnahyeon.pop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.example.kimnahyeon.pop.for_sql.DBManager;


public class SetActivity extends AppCompatActivity {

    Switch setSwitch;
    ImageButton backImg;
    Button saveBtn;

    DBManager dbManager;
    Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        dbManager=new DBManager(this);
        manager=new Manager();

        setSwitch=(Switch)findViewById(R.id.set_switch);
        backImg=(ImageButton)findViewById(R.id.set_image);
        saveBtn=(Button)findViewById(R.id.set_save_btn);

        dbManager.insertSettings(new Manager());

        if(Manager.isAlways()==true) setSwitch.setChecked(true);
        else setSwitch.setChecked(false);

        setSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    //스위치 on
                    Manager.setIsAlways(true);
                    manager.setIsAlways(true);

                }else{
                    //스위치 off
                    Manager.setIsAlways(false);
                    manager.setIsAlways(false);
                }
                dbManager.updateSettings(manager);
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }


}
