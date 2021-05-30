package com.capstone.puppy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.PuppyInfo;
import com.capstone.puppy.util.DogeDB;

public class ModifyPuppyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_image;
    EditText et_name;
    EditText et_age;
    Button btn_add;

    PuppyInfo puppyInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_modify_puppy);

        //View 객체 와 ID 연결
        et_image = findViewById(R.id.et_image);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        btn_add = findViewById(R.id.btn_add);

        //View 이벤트 등록
        btn_add.setOnClickListener(this);

        Intent intent = getIntent();
        puppyInfo = intent.getParcelableExtra("puppy");
        et_image.setText(puppyInfo.getImageUrl());
        et_name.setText(puppyInfo.getName());
        et_age.setText(puppyInfo.getAge());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 강아지 이름을 입력받고 저장하기.
            case R.id.btn_add:
                String url = et_image.getText().toString();
                String name =  et_name.getText().toString();
                String age = et_age.getText().toString();

                puppyInfo.updateInfo(name, age, url);
                DogeDB.updateRecord(name, age, url, puppyInfo.getId());
                Intent intent = getIntent();
                intent.putExtra("puppy", puppyInfo);
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.iv_thumnail:
                break;
        }
    }
}