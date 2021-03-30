package com.capstone.puppy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.capstone.puppy.Items.ListViewAdapter;
import com.capstone.puppy.Items.ListViewItem;

public class SelectPuppyActivity extends AppCompatActivity implements View.OnClickListener {

    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_puppy);
        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image),
                "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image),
                "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image),
                "Ind", "Assignment Ind Black 36dp") ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                Drawable iconDrawable = item.getIcon() ;

                // TODO : use item data.
            }
        });
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 버튼을 누르면 애드퍼피 페이지로 이
            case R.id.button1:
                Intent intent = new Intent(this, AddPuppyActivity.class);
                startActivity(intent);

                break;

        }
    }
}