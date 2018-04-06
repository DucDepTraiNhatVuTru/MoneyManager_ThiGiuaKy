package com.example.dell.qltc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.qltc.Adapter.DateListAdapter;
import com.example.dell.qltc.DoGetMoneyLog;
import com.example.dell.qltc.InstanceGet;
import com.example.dell.qltc.R;
import com.example.dell.qltc.datamodel.MoneyLog;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class SoGiaoDichActivity extends AppCompatActivity {
    FloatingActionButton fab ;
    ListView lvLogs;
    TextView txtvThu,txtvChi,txtvTong;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_option_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemXuHuong:
                Intent intent = new Intent(SoGiaoDichActivity.this,XuHuongActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history_this_month);
        setTitle("Tháng này");
        anhXa();
        Calendar c = GregorianCalendar.getInstance();
        int m = 1 +c.get(Calendar.MONTH);
        new DoGetMoneyLog().execute("http://192.168.42.107:50000/api/MoneyLogs?month="+m);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Toast.makeText(SoGiaoDichActivity.this,InstanceGet.moneyLogs.size(),Toast.LENGTH_LONG).show();
        DateListAdapter adapter= new DateListAdapter(SoGiaoDichActivity.this,R.layout.layout_item_list_log, InstanceGet.moneyLogs);
        lvLogs.setAdapter(adapter);

        tinh();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SoGiaoDichActivity.this,ThemGiaoDichActivity.class);
                startActivity(intent);
            }
        });
    }
    private void anhXa(){
        fab = findViewById(R.id.fbtnAdd);
        lvLogs = findViewById(R.id.lvHistoryThisMonth);
        txtvThu = findViewById(R.id.txtvTienVao);
        txtvChi = findViewById(R.id.txtvTienRa);
        txtvTong = findViewById(R.id.txtvTienConLai);
    }

    void tinh(){
        int thu=0;
        int chi=0;
        for (MoneyLog moneylog:InstanceGet.moneyLogs
             ) {
            if(moneylog.isComes()==true){
                chi-=moneylog.getAmount();
            }
            else {
                thu+=moneylog.getAmount();
            }
        }
        txtvThu.setText(String.valueOf(thu));
        txtvChi.setText(String.valueOf(chi));
        int tong = thu + chi;
        txtvTong.setText(String.valueOf(tong));
    }

}
