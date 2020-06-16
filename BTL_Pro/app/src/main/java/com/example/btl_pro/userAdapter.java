package com.example.btl_pro;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class userAdapter extends BaseAdapter {
    private Context context;
    // đối tượng để nạp dữ liệu
    private LayoutInflater layoutInflater;
    private ArrayList<userBean> dataList;
    public userAdapter(Activity activity, ArrayList<userBean> dataList) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataList = dataList;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v =layoutInflater.inflate(R.layout.item_user, null);
            TextView userName = (TextView) v.findViewById(R.id.userName);
            userName.setText(dataList.get(position).getUserName());
            TextView passWord = (TextView) v.findViewById(R.id.passWord);
            passWord.setText(dataList.get(position).getPassWord());
            final Switch status = (Switch) v.findViewById(R.id.status);
            status.setChecked(dataList.get(position).isStatus());
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataList.get(position).setStatus(!dataList.get(position).isStatus());
                }
            });
        }
        return v;
    }
}
