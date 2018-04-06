package com.example.dell.qltc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.dell.qltc.R;
import com.example.dell.qltc.datamodel.MoneyLog;

import java.util.LinkedList;

public class MonthListAdapter extends BaseAdapter {
    Context context;
    int resource;
    LinkedList<DateListAdapter> listAdapters;
    LinkedList<MoneyLog> moneyLogs;

    public MonthListAdapter(Context context, int resource, LinkedList<DateListAdapter> listAdapters, LinkedList<MoneyLog> moneyLogs) {
        this.context = context;
        this.resource = resource;
        this.listAdapters = listAdapters;
        this.moneyLogs = moneyLogs;
    }

    @Override
    public int getCount() {
        return listAdapters.size();
    }

    @Override
    public Object getItem(int position) {
        return listAdapters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(this.resource,parent);
        ListView lvHistoryMonth;
        lvHistoryMonth = convertView.findViewById(R.id.lvHistoryThisMonth);



        return convertView;
    }
}
