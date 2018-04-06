package com.example.dell.qltc.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.qltc.R;
import com.example.dell.qltc.datamodel.MoneyLog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class DateListAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private LinkedList<MoneyLog> moneyLogs;

    public DateListAdapter(Context context, int resource, LinkedList<MoneyLog> moneyLogs) {
        this.context = context;
        this.resource = resource;
        this.moneyLogs = moneyLogs;
    }

    @Override
    public int getCount() {
        return moneyLogs.size();
    }

    @Override
    public Object getItem(int position) {
        return moneyLogs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(this.resource,null);
        ImageView imgvHinh;
        TextView txtvContent, txtvNote, txtvAmount, txtvDate;
        imgvHinh = convertView.findViewById(R.id.imgvIcon);
        txtvContent = convertView.findViewById(R.id.txtvItemContent);
        txtvNote = convertView.findViewById(R.id.txtvItemNote);
        txtvAmount = convertView.findViewById(R.id.txtvItemAmount);
        txtvDate =convertView.findViewById(R.id.txtvItemDate);
        if(moneyLogs.get(position).isComes()==true){
            imgvHinh.setImageResource(R.drawable.chi);
        }else {
            imgvHinh.setImageResource(R.drawable.thu);
        }
        txtvContent.setText(moneyLogs.get(position).getContent());
        txtvNote.setText(moneyLogs.get(position).getNote());
        txtvAmount.setText(String.valueOf(moneyLogs.get(position).getAmount()));
        txtvDate.setText(moneyLogs.get(position).getDate());
        return convertView;
    }
}
