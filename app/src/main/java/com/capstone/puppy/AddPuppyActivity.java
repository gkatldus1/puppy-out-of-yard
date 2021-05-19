package com.capstone.puppy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.PuppyInfo;

public class AddPuppyActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_puppy;
    EditText et_name;
    EditText et_age;
    Button btn_add;


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 강아지 이름을 입력받고 저장하기.
            case R.id.btn_add:
//                String puppy_image = iv_puppy;
                String puppy_image = "temp";
                String puppy_name =  et_name.getText().toString();
                String puppy_age = et_age.getText().toString();

                Intent intent = getIntent();
                intent.putExtra("url", puppy_image);
                intent.putExtra("name", puppy_name);
                intent.putExtra("age", puppy_age);
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.iv_thumnail:
                break;
        }
    }
}