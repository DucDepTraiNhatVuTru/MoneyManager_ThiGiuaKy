package com.example.dell.qltc.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.dell.qltc.DoPostMoneyLog;
import com.example.dell.qltc.InstanceGet;
import com.example.dell.qltc.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ThemGiaoDichActivity extends AppCompatActivity {

    EditText edtAmount, edtContent, edtNote, edtDate;
    RadioButton rdbIncomes, rdbOutcomes;
    Button btnLuu;
    Dialog dialog;
    boolean comes= true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_money_log);
        setTitle("Thêm giao dịch");
        anhXa();


        Calendar calendar = GregorianCalendar.getInstance();
        int m = calendar.get(Calendar.MONTH);
        edtDate.setText(String.valueOf(calendar.get(Calendar.YEAR))
                + "-" + String.valueOf(1 + m)
                + "-" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ThemGiaoDichActivity.this);
                dialog.setContentView(R.layout.layout_date_picker);

                TextView txtvHuy = dialog.findViewById(R.id.txtvHuy);
                TextView txtPick = dialog.findViewById(R.id.txtvPick);
                final DatePicker datePicker = dialog.findViewById(R.id.dpTime);
                txtvHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                txtPick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = GregorianCalendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        final int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                            @Override
                            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                int m = monthOfYear +1;
                                edtDate.setText(String.valueOf(year) + "-" + String.valueOf(m) + "-" + String.valueOf(dayOfMonth));
                                dialog.cancel();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });


        rdbIncomes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    comes=false;
                    rdbOutcomes.setChecked(false);
                }
            }
        });

        rdbOutcomes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    comes=true;
                    rdbIncomes.setChecked(false);
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtAmount.getText().toString()==null
                        ||edtContent.getText().toString()==null
                        ||edtNote.getText().toString()==null
                        ||edtDate.getText().toString()==null){
                    thongBao("Thông báo","Vui lòng nhập đầy đủ thông tin");
                }else {
                    new DoPostMoneyLog().execute("http://192.168.42.107:50000/api/MoneyLogs", InstanceGet.userLogin,edtAmount.getText().toString(),
                            String.valueOf(comes),edtContent.getText().toString(),
                            edtNote.getText().toString(),edtDate.getText().toString());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    InstanceGet.moneyLogs.clear();
                    finish();
                    Intent intent = new Intent(ThemGiaoDichActivity.this,SoGiaoDichActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void anhXa() {
        edtAmount = findViewById(R.id.edtAmount);
        edtContent = findViewById(R.id.edtContent);
        edtNote = findViewById(R.id.edtNote);
        edtDate = findViewById(R.id.edtDate);
        rdbIncomes = findViewById(R.id.rdbThu);
        rdbOutcomes = findViewById(R.id.rdbChi);
        btnLuu = findViewById(R.id.btnLuu);
    }

    private void thongBao(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThemGiaoDichActivity.this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtAmount.setText("");
                edtContent.setText("");
                edtNote.setText("");
                Calendar calendar = GregorianCalendar.getInstance();
                edtDate.setText(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
