package com.capstone.puppy.PuppyInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.capstone.puppy.R;

import java.util.ArrayList;

public class SelectPuppyAdapter extends BaseAdapter {
    private ArrayList<PuppyInfo> puppys = null;
    LayoutInflater inflater = null;
    private int puppy_count = 0;

    public SelectPuppyAdapter(ArrayList<PuppyInfo> puppys){
        this.puppys = puppys;
        puppy_count = puppys.size();
    }

    @Override
    public int getCount() {
        return puppy_count;
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
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }


        TextView tv_puppy_name = (TextView)view.findViewById(R.id.tv_puppy_name);
        TextView tv_puppy_age = (TextView)view.findViewById(R.id.tv_puppy_age);
//        ImageView tv_puppy_picture = (ImageView)view.findViewById(R.id.tv_puppy_picture);

        tv_puppy_name.setText(puppys.get(i).getName());
        tv_puppy_age.setText(puppys.get(i).getAge());
//        tv_puppy_picture.setImageDrawable(p);

        return view;
    }
}
