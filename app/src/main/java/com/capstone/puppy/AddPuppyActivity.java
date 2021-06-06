package com.capstone.puppy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.PuppyInfo;
import com.capstone.puppy.util.DogeDB;

import java.util.ArrayList;

public class AddPuppyActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "AddPuppyActivity";
    ImageView iv_puppy;
    EditText et_name;
    EditText et_age;
    Button btn_add;
    ArrayList<PuppyInfo> puppys;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_add_puppy);

        //View 객체 와 ID 연결
        iv_puppy = findViewById(R.id.iv_thumnail);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        btn_add = findViewById(R.id.btn_add);

        //View 이벤트 등록
        iv_puppy.setOnClickListener(this);
        btn_add.setOnClickListener(this);

        Intent intent = getIntent();
        puppys = intent.getParcelableArrayListExtra("puppys");
        Log.i(TAG, "puppys.size():" + puppys.size());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 강아지 이름을 입력받고 저장하기.
            case R.id.btn_add:
//                String puppy_image = iv_puppy;
                String name = et_name.getText().toString();
                String age = et_age.getText().toString();
                String url = "temp";
                int id = -1;
                for(int i = 0; i < puppys.size(); i++){
                    if( id < puppys.get(i).getId())
                        id = puppys.get(i).getId();
                }

                PuppyInfo puppy = new PuppyInfo(id+1, name, age, url);
                DogeDB.insertRecord("DOG_INFO", puppy.getName(), puppy.getAge(), "");
                puppys.add(puppy);

                Log.i(TAG, "puppys.size():" + puppys.size());
                Intent intent = getIntent();
                intent.putParcelableArrayListExtra("puppys", puppys);
                setResult(RESULT_OK, intent);
                Log.i(TAG, "puppys.size():" + puppys.size());
                finish();

                break;
            case R.id.iv_thumnail:
                break;
        }
    }
}