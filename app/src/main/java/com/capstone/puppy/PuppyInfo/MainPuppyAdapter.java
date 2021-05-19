package com.capstone.puppy.PuppyInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.capstone.puppy.R;

import java.util.ArrayList;

public class MainPuppyAdapter extends BaseAdapter {
    private ArrayList<PuppyInfo> puppys = null;
    LayoutInflater inflater = null;

    public MainPuppyAdapter(ArrayList<PuppyInfo> puppys){
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
        {
            final Context context = viewGroup.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            view = inflater.inflate(R.layout.main_puppy_item, viewGroup, false);
        }

        CheckBox cb_puppy_select = (CheckBox)view.findViewById(R.id.ch_puppy_select);
        TextView tv_puppy_name = (TextView)view.findViewById(R.id.tv_puppy_name);
        TextView tv_puppy_distance = (TextView)view.findViewById(R.id.tv_puppy_distance);

        cb_puppy_select.setChecked(false);
        tv_puppy_name.setText(puppys.get(i).getName());
        tv_puppy_distance.setText(puppys.get(i).getDistance());

        return view;
    }
}
