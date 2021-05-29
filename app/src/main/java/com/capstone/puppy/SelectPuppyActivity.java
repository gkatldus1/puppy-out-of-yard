package com.capstone.puppy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.puppy.PuppyInfo.PuppyInfo;
import com.capstone.puppy.PuppyInfo.SelectPuppyAdapter;

import java.util.ArrayList;

public class SelectPuppyActivity extends AppCompatActivity implements View.OnClickListener   {
    private final String TAG = "SelectPuppyActivity";
    Button btn_add, btn_mod, btn_del;
    private ListView lv_puppys_select;
    ArrayList<PuppyInfo> puppys;
    ArrayList<PuppyInfo> added_puppys = null;
    SelectPuppyAdapter puppyAdapter;

    @Override
    public void onBackPressed(){
        Log.i(TAG, "onBackPressed()");
        if(added_puppys == null){
            Log.i(TAG, "Added_Puppy is null");
        }else{
            Intent intent = getIntent();
            intent.putExtra("puppys", puppys);
            Log.i(TAG, "Added_Puppy is not null");
            intent.putExtra("added_puppys", added_puppys);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

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
                PuppyInfo puppy = (PuppyInfo)puppyAdapter.getItem(position);
                puppy.setChecked(!puppy.getChecked());
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
        for(int i = puppyAdapter.getCount()-1; i>=0; i--){
            PuppyInfo puppy = (PuppyInfo)puppyAdapter.getItem(i);
            if(puppy.getChecked()){
                puppys.remove(i);
            }
        }
        puppyAdapter.notifyDataSetChanged();
    }

    public void onAddClick(){
        // 아이템 추가.
        Intent intent = new Intent(this, AddPuppyActivity.class);
        intent.putExtra("puppys", puppys);//데이터 보내주는 코드
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                String url = data.getStringExtra("url");
                String name = data.getStringExtra("name");
                String age = data.getStringExtra("age");

                Log.i(TAG, "intent data:" + url + " " + name + " " + age);
                int id = 0;
                for(int i = 0; i < puppys.size(); i++){
                    if( id < puppys.get(i).getId())
                        id = puppys.get(i).getId();
                }
                PuppyInfo puppy = new PuppyInfo(id+1, url, name, age);
                puppys.add(puppy);
                puppyAdapter.notifyDataSetChanged();

                if(added_puppys == null) {
                    added_puppys = new ArrayList<>();
                    Log.i(TAG, "added_puppy new instance");
                }
                added_puppys.add(puppy);
                Log.i(TAG, "added_puppy added");
            }
        }
    }

//    public void onModifyClick(){
//        Intent intent = new Intent(this, ModifyPuppyActivity.class);
//        intent.putExtra("puppys", puppys);//데이터 보내주는 코드
//        startActivityForResult(intent, 1);
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                onAddClick();
                break;
//            case R.id.btn_mod:
//                onModifyClick();
//                break;
            case R.id.btn_del:
                onDeleteClick();
                break;
        }
    }
}




