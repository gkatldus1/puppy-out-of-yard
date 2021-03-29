package com.capstone.puppy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddPuppyActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv_puppy;
    Button btn_add;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_add_puppy);
        //강아지 추가 버튼 이벤트 리스너
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        //강아지 사진 추가 버튼 이벤트 리스너
        iv_puppy = findViewById(R.id.iv_puppy);
        iv_puppy.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 강아지 이름을 입력받고 저장하기.
            case R.id.btn_add:
                EditText puppyName = (EditText)findViewById(R.id.editTextTextPersonName);
                name = puppyName.getText().toString();
                Toast.makeText(this.getApplicationContext(),name, Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_puppy:
                //앨범 불러와서 사진 저장시키는 코드
                break;
        }
    }


//잠깐 수정해봄


}