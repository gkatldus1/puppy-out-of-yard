package com.capstone.puppy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.capstone.puppy.PuppyInfo.PuppyInfo;

import java.util.ArrayList;




public class SelectPuppyActivity extends AppCompatActivity  {

    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"};
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_puppy);

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<PuppyInfo> puppys = (ArrayList<PuppyInfo>) intent.getSerializableExtra("puppys");
            if (puppys != null) {
                Toast.makeText(getApplicationContext(), "전달 받은 ArrayList의 첫번째 요소 :" + puppys.get(0).getName(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}




