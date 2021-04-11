package com.capstone.puppy.PuppyInfo;

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

public class SelectPuppyAdapter extends BaseAdapter {
    private ArrayList<PuppyInfo> puppys = null;
    LayoutInflater inflater = null;
    private int puppy_count = 0;
    boolean[] mCheckArray;  //check box


    public SelectPuppyAdapter(ArrayList<PuppyInfo> puppys){
        this.puppys = puppys;
        puppy_count = puppys.size();
        if(puppy_count > 0) //check box
            mCheckArray = new boolean[puppys.size()];
    }
//    public SelectPuppyAdapter(Context context, int textViewResourceID, ArrayList<PuppyInfo> puppys){
//        this.context = context;
//        this.layout = textViewResourceID;
//        this.puppys = puppys;
//        puppy_count = puppys.size();
//    }




    public boolean getChecked(int position){
        return mCheckArray[position];
    }
    public void setCheck(int position){
        mCheckArray[position] = !mCheckArray[position];
    }
    public void setAllCheck(boolean value){
        for(int i = 0; i < mCheckArray.length; i++){
            mCheckArray[i] = value;
        }
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
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }


        TextView tv_puppy_name = (TextView)view.findViewById(R.id.tv_puppy_name);
        TextView tv_puppy_age = (TextView)view.findViewById(R.id.tv_puppy_age);
        ImageView iv_puppy_pic = (ImageView)view.findViewById(R.id.iv_puppy_pic);
        CheckBox cb_puppy = (CheckBox)view.findViewById(R.id.cb_puppy);    //checkbox 관련

        tv_puppy_name.setText(puppys.get(i).getName());
        tv_puppy_age.setText(puppys.get(i).getAge() + "");
        iv_puppy_pic.setImageResource(R.drawable.pawprint);
        cb_puppy.setChecked(mCheckArray[i]);



        return view;
    }
}
