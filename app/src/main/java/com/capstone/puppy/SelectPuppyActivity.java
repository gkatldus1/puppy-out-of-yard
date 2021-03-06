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
import com.capstone.puppy.util.DogeDB;

import java.util.ArrayList;

public class SelectPuppyActivity extends AppCompatActivity implements View.OnClickListener   {
    private final String TAG = "SelectPuppyActivity";
    Button btn_add, btn_mod, btn_del;
    private ListView lv_puppys_select;
    ArrayList<PuppyInfo> puppys = null;
    SelectPuppyAdapter puppyAdapter;
    int selectedPosition = -1;

    @Override
    public void onBackPressed(){
        Log.i(TAG, "onBackPressed()");

        Intent intent = getIntent();
        intent.putExtra("puppys", puppys);
        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_puppy);

        Intent intent = getIntent();
        if (intent != null) {
            puppys = intent.getParcelableArrayListExtra("puppys");
            if (puppys != null
            && puppys.size() > 0) {
                Log.i(TAG, "puppys.size():" + puppys.size());
            }
        }

        puppyAdapter = new SelectPuppyAdapter(puppys);
        lv_puppys_select = findViewById(R.id.lv_puppys_select);
        lv_puppys_select.setAdapter(puppyAdapter);

        //checkbox click listener
        lv_puppys_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
                puppyAdapter.setSelectedItemPosition(position);

                PuppyInfo puppy = (PuppyInfo)puppyAdapter.getItem(position);
                puppy.setChecked(!puppy.getChecked());
                puppyAdapter.notifyDataSetChanged();
            }
        });

        //btn ?????????
        btn_add = findViewById(R.id.btn_add);
        btn_mod = findViewById(R.id.btn_mod);
        btn_del = findViewById(R.id.btn_del);
        // ????????? ????????? ??????
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
        // ????????? ??????.
        Intent intent = new Intent(this, AddPuppyActivity.class);
        intent.putParcelableArrayListExtra("puppys", puppys);//????????? ???????????? ??????
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                    puppys = data.getParcelableArrayListExtra("puppys");
                    puppyAdapter = new SelectPuppyAdapter(puppys);
                    lv_puppys_select.setAdapter(puppyAdapter);
                    puppyAdapter.notifyDataSetChanged();
                    Log.i(TAG, "puppy ui update");
                    break;
                case 2:
                    PuppyInfo new_puppy = data.getParcelableExtra("puppy");
                    PuppyInfo old_puppy = puppys.get(selectedPosition);
                    old_puppy.setName(new_puppy.getName());
                    old_puppy.setAge(new_puppy.getAge());
                    old_puppy.setImageUrl(new_puppy.getImageUrl());
                    puppyAdapter = new SelectPuppyAdapter(puppys);
                    lv_puppys_select.setAdapter(puppyAdapter);
                    puppyAdapter.notifyDataSetChanged();
                    Log.i(TAG, "puppy ui update");
                    break;
            }
        }
    }

    public void onModifyClick(){
        if(selectedPosition != -1) {
            PuppyInfo puppy = puppys.get(selectedPosition);
            Intent intent = new Intent(this, ModifyPuppyActivity.class);
            intent.putExtra("puppy", puppy);//????????? ???????????? ??????
            startActivityForResult(intent, 2);
        }
    }

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




