package com.example.daandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daandroid.utls.DatabaseHandler;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        addEvents();
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        SQLiteDatabase readableDatabase = databaseHandler.getReadableDatabase();
        readableDatabase.close();
    }

    private void addControls() {
        etUsername=findViewById(R.id.etUserName);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
    }
    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangNhap();
            }
        });
    }

    private void xuLyDangNhap() {
        if(etUsername.getText().length() != 0 && etPassword.getText().length() != 0) {

            if (etUsername.getText().toString().equals("admin") && etPassword.getText().toString().equals("admin")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(
                        LoginActivity.this,
                        "Sai thông tin đăng nhập",
                        Toast.LENGTH_LONG
                ).show();
            }
        }
        else
        {
            Toast.makeText(
                    LoginActivity.this,
                    "Vui lòng nhập đủ thông tin",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}