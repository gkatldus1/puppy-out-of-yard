package com.capstone.puppy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.PuppyInfo;
import com.capstone.puppy.PuppyInfo.SelectPuppyAdapter;

import java.util.ArrayList;




public class SelectPuppyActivity extends AppCompatActivity implements View.OnClickListener   {

    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"};
    Button btn_add, btn_mod, btn_del;
    private ListView lv_puppys_select;
    ArrayList<PuppyInfo> puppys;
    SelectPuppyAdapter puppyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_puppy);

        Intent intent = getIntent();
        if (intent != null) {
            puppys = (ArrayList<PuppyInfo>) intent.getSerializableExtra("puppys");
            if (puppys != null) {
                Toast.makeText(getApplicationContext(), "전달 받은 ArrayList의 첫번째 요소 :" + puppys.get(0).getName(), Toast.LENGTH_SHORT).show();
            }
        }

        puppyAdapter = new SelectPuppyAdapter(puppys);
        lv_puppys_select = findViewById(R.id.lv_puppys_select);
        lv_puppys_select.setAdapter(puppyAdapter);

        //checkbox click listener
        lv_puppys_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                puppyAdapter.setCheck(position);
                puppyAdapter.notifyDataSetChanged();
            }
        });

       //btn 초기화
        btn_add = findViewById(R.id.btn_add);
        btn_mod = findViewById(R.id.btn_mod);
        btn_del = findViewById(R.id.btn_del);
        // 이벤트 핸들러 연결
        btn_add.setOnClickListener(this);
        btn_mod.setOnClickListener(this);
        btn_del.setOnClickListener(this);



    }

    //checkbox
    public void onDeleteClick(){
        boolean bCheck;
        for(int i = puppyAdapter.getCount()-1; i>=0; i--){
            bCheck = puppyAdapter.getChecked(i);
            if(bCheck == true){
                puppys.remove(i);
            }
        }
        puppyAdapter.notifyDataSetChanged();
    }
    public void onAddClick(){}
    public void onModifyClick(){}


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                onAddClick();
                break;
            case R.id.btn_mod:
                onModifyClick();
                break;
            case R.id.btn_del:
                onDeleteClick();
                break;
        }
    }
}




