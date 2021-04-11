package com.capstone.puppy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.PuppyInfo;

import java.util.ArrayList;

public class AddPuppyActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_puppy;
    Button btn_add;
    String name;
    ArrayList<PuppyInfo> puppys;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_add_puppy);

        //강아지 추가 버튼 이벤트 리스너
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        //강아지 사진 추가 버튼 이벤트 리스너

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 강아지 이름을 입력받고 저장하기.
            case R.id.btn_add:
                Intent intent = getIntent();

                ImageView image = (ImageView) findViewById(R.id.image);
                EditText name = (EditText) findViewById(R.id.editName);
                EditText age = (EditText) findViewById(R.id.editName);

                //String puppy_image = image;
                String puppy_name =  name.getText().toString();
                String puppy_age = age.getText().toString();

                PuppyInfo puppy = new PuppyInfo("", puppy_name, puppy_age);
                intent.putExtra("puppy", puppy);
                setResult(RESULT_OK,intent);
                finish();
                break;

        }
    }


//잠깐 수정해봄


}