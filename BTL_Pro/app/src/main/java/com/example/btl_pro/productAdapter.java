package com.example.btl_pro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class productAdapter extends BaseAdapter {
    private Context context;
    // đối tượng để nạp dữ liệu
    private LayoutInflater layoutInflater;
    private ArrayList<productBean> dataList;
    contactNow contactNow;
    public productAdapter(Activity activity, ArrayList<productBean> dataList, contactNow ctNow) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataList = dataList;
        contactNow = ctNow;
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
            v =layoutInflater.inflate(R.layout.item_product, null);
            TextView tsp = (TextView) v.findViewById(R.id.tenSp);
            tsp.setText(dataList.get(position).getNamePro());
            TextView tSdt = (TextView) v.findViewById(R.id.sdt);
            tSdt.setText(dataList.get(position).getPhone());
            TextView cost = (TextView) v.findViewById(R.id.tCost);
            cost.setText(dataList.get(position).getCost() +" VNĐ");
            TextView namePer = (TextView) v.findViewById(R.id.namePer);
            namePer.setText(dataList.get(position).getNamePer());
            Button buttonEdit = (Button) v.findViewById(R.id.button_edit);
            if (dataList.get(position).getNamePer().equals(commonUtil.userName)) {
                buttonEdit.setVisibility(View.VISIBLE);
            }
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactNow.onclickEdit(dataList.get(position));
                }
            });
            ImageView imageView = (ImageView) v.findViewById(R.id.iImage_view);
            Uri imageUri = Uri.parse(dataList.get(position).getSrcImage());
            Picasso.get().load(imageUri).into(imageView);
            Button ctNow = (Button) v.findViewById(R.id.contactNow);
            ctNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = dataList.get(position).getPhone();
                    if(!commonUtil.isNullOrEmpTy(phone)) {
                        contactNow.onClickContactNow(phone);
                    }
                }
            });
            Switch status = (Switch) v.findViewById(R.id.status);
            status.setChecked(dataList.get(position).isStatus());
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataList.get(position).setStatus(!dataList.get(position).isStatus());
                    contactNow.onclickStatus(dataList);
                }
            });
            if (commonUtil.userName.equals("admin")) {
                status.setVisibility(View.VISIBLE);
            }
        }
        return v;
    }
}
