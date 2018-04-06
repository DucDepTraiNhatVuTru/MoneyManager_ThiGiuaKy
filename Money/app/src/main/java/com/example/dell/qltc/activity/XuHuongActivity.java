package com.example.dell.qltc.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.FontsContract;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.Cartesian3d;
import com.anychart.anychart.Column3d;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.example.dell.qltc.Adapter.DateListAdapter;
import com.example.dell.qltc.DoGetMoneyLog;
import com.example.dell.qltc.InstanceGet;
import com.example.dell.qltc.R;
import com.example.dell.qltc.datamodel.MoneyLog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

public class XuHuongActivity extends AppCompatActivity {
    Spinner spnThuChi;
    TextView txtvTu, txtvDen;
    ListView lvDanhSach;
    Dialog dialog;
    Button btnFilt;
    AnyChartView anyChartView;
    boolean thuchi = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xu_huong);
        anhXa();
        final List<String> list = new ArrayList<>();
        list.add("Thu");
        list.add("Chi");

        final ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnThuChi.setAdapter(adapter);

        spnThuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnThuChi.getSelectedItem()=="Thu"){
                    thuchi=false;
                }
                else{
                    thuchi=true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar calendar = GregorianCalendar.getInstance();
        int m = calendar.get(Calendar.MONTH) +1;
        txtvTu.setText(calendar.get(Calendar.YEAR)+"-"+m+"-"+calendar.get(Calendar.DAY_OF_MONTH));
        txtvDen.setText(calendar.get(Calendar.YEAR)+"-"+m+"-"+calendar.get(Calendar.DAY_OF_MONTH));
        txtvTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(XuHuongActivity.this);
                dialog.setContentView(R.layout.layout_date_picker);

                TextView txtvHuy = dialog.findViewById(R.id.txtvHuy);
                TextView txtPick = dialog.findViewById(R.id.txtvPick);
                final DatePicker datePicker = dialog.findViewById(R.id.dpTime);

                final Calendar calendar = GregorianCalendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int[] yyyy = {0};
                final int[] MM = {0};
                final int[] dd = {0};
                datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        yyyy[0] = year;
                        MM[0] =monthOfYear+1;
                        dd[0] = dayOfMonth;
                        txtvTu.setText(yyyy[0]+"-"+MM[0]+"-"+dd[0]);
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
        txtvDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(XuHuongActivity.this);
                dialog.setContentView(R.layout.layout_date_picker);

                TextView txtvHuy = dialog.findViewById(R.id.txtvHuy);
                TextView txtPick = dialog.findViewById(R.id.txtvPick);
                final DatePicker datePicker = dialog.findViewById(R.id.dpTime);

                final Calendar calendar = GregorianCalendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int[] yyyy = {0};
                final int[] MM = {0};
                final int[] dd = {0};
                datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        yyyy[0] = year;
                        MM[0] =monthOfYear+1;
                        dd[0] = dayOfMonth;
                        txtvDen.setText(yyyy[0]+"-"+MM[0]+"-"+dd[0]);
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
        });
        btnFilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoGetMoneyLog().execute("http://192.168.42.107:50000/api/MoneyLogs?comes="+thuchi+"&from="+txtvTu.getText()+
                "&to="+txtvDen.getText());
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DateListAdapter adapter1 = new DateListAdapter(XuHuongActivity.this,R.layout.layout_item_list_log, InstanceGet.moneyLogs);
                lvDanhSach.setAdapter(adapter1);
                lvDanhSach.deferNotifyDataSetChanged();
                updateChart();

            }
        });
    }

    private void anhXa(){
        spnThuChi = findViewById(R.id.spnThuChi);
        txtvTu = findViewById(R.id.txtvTuNgay);
        txtvDen = findViewById(R.id.txtvDenNgay);
        lvDanhSach = findViewById(R.id.lvlichsu);
        btnFilt = findViewById(R.id.btnFilt);
        anyChartView = findViewById(R.id.chartThang);
    }

    private void updateChart(){
        int tuThang=0;
        int denThang;
        String[] tach = txtvTu.getText().toString().split("-");
        tuThang=Integer.parseInt(tach[1]);
        tach = txtvDen.getText().toString().split("-");
        denThang=Integer.parseInt(tach[1]);

        Dictionary<String,Integer> tongKet = new Hashtable<>();
        ArrayList<Integer> listTong = new ArrayList<>();
        int tong=0;
        for(int i = tuThang; i<=denThang; i++){
            for (MoneyLog log: InstanceGet.moneyLogs
                    ) {
                tach = log.getDate().split("-");
                if(Integer.parseInt(tach[1])==i){
                    tong += log.getAmount();
                }
            }
            tongKet.put(String.valueOf(i),tong);
            tong=0;
        }
        Cartesian columns = AnyChart.column();
        ArrayList<DataEntry> entries = new ArrayList<>();
        for(int i=tuThang; i<=denThang;i++){
            entries.add(new ValueDataEntry(i,tongKet.get(String.valueOf(i))));
        }
        columns.setData(entries);
        anyChartView.setChart(columns);
    }
}
