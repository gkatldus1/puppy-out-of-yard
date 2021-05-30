package com.capstone.puppy.PuppyInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.puppy.R;

import java.util.ArrayList;

import static com.capstone.puppy.R.color.purple_200;

public class SelectPuppyAdapter extends BaseAdapter {
    private ArrayList<PuppyInfo> puppys = null;
    LayoutInflater inflater = null;
    int selectedItemPosition = -1;

    public void setSelectedItemPosition(int position){
        this.selectedItemPosition = position;
    }

    public int getSelectedItemPosition(){
        return selectedItemPosition;
    }

    public SelectPuppyAdapter(ArrayList<PuppyInfo> puppys){
        this.puppys = puppys;
    }

    @Override
    public int getCount() {
        return puppys.size();
    }

    @Override
    public Object getItem(int i) {
        return puppys.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            final Context context = viewGroup.getContext();
            if (inflater == null)
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }
        if (i == selectedItemPosition){
            view.setBackgroundColor(purple_200);
        }else{
            view.setBackgroundColor(0);
        }

        TextView tv_puppy_name = (TextView)view.findViewById(R.id.tv_puppy_name);
        TextView tv_puppy_age = (TextView)view.findViewById(R.id.tv_puppy_age);
        ImageView iv_puppy_pic = (ImageView)view.findViewById(R.id.iv_puppy_pic);
        CheckBox cb_puppy = (CheckBox)view.findViewById(R.id.cb_puppy);    //checkbox 관련

        tv_puppy_name.setText(puppys.get(i).getName());
        tv_puppy_age.setText(puppys.get(i).getAge());
        iv_puppy_pic.setImageResource(R.drawable.pawprint);
        cb_puppy.setChecked(puppys.get(i).getChecked());

        return view;
    }
}
