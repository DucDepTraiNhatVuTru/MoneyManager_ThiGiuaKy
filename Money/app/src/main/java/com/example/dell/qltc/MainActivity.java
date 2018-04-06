package com.example.dell.qltc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.qltc.activity.SignUpActivity;
import com.example.dell.qltc.activity.SoGiaoDichActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail,edtPass;
    Button btnSignIn;
    TextView txtCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoGet().execute("http://192.168.42.107:50000/api/UserAccounts?email="+edtEmail.getText()
                        +"&password="+edtPass.getText());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(InstanceGet.soLuongKetQua==1){
                    InstanceGet.userLogin=edtEmail.getText().toString();
                    Intent intent = new Intent(MainActivity.this, SoGiaoDichActivity.class);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Thông báo!");
                    builder.setMessage("Sai tài khoản hoặc mật khẩu! Vui lòng kiểm tra lại!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edtPass.setText("");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


    private void anhXa(){
        edtEmail = findViewById(R.id.edtDangNhapEmail);
        edtPass=findViewById(R.id.edtDangNhapPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        txtCreateAccount=findViewById(R.id.txtvCreateAccount);
    }
}
