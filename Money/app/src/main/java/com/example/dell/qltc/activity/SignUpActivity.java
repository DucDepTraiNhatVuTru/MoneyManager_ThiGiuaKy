package com.example.dell.qltc.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.dell.qltc.DoPost;
import com.example.dell.qltc.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static java.time.LocalDate.*;

public class SignUpActivity extends AppCompatActivity {
    EditText edtSignUpEmail, edtSignUpPassword, edtSignUpConfirmPassword;
    Button btnDone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up);
        setTitle("Create new an account");
        anhXa();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSignUpEmail.getText().toString() == ""
                        || edtSignUpPassword.getText().toString() == ""
                        || edtSignUpConfirmPassword.getText().toString() == "") {
                    thongBao("Thông báo", "Hãy điền đầy đủ thông tin!");
                } else if (edtSignUpPassword.getText().toString() != edtSignUpConfirmPassword.getText().toString()) {
                    thongBao("Thông báo", "Mật khẩu không khớp! Vui lòng kiểm tra lại!");
                } else {
                    Calendar c = Calendar.getInstance();
                    new DoPost().execute("http://192.168.42.107:50000/api/UserAccounts", edtSignUpEmail.getText().toString(), edtSignUpPassword.getText().toString(), c.getTime().toString());

                }
            }
        });
    }

    private void anhXa() {
        edtSignUpEmail = findViewById(R.id.edtEmailSignUp);
        edtSignUpPassword = findViewById(R.id.edtPassSignUp);
        edtSignUpConfirmPassword = findViewById(R.id.edtConfirmPassSignUp);
        btnDone = findViewById(R.id.btnSignUp);
    }

    private void thongBao(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtSignUpPassword.setText("");
                edtSignUpConfirmPassword.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
