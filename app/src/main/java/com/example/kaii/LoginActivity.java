package com.example.kaii;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import okhttp3.Request;

public class LoginActivity extends AppCompatActivity {
    EditText idView, passwordView;
    CheckBox checkAutoLogin;
    String id, password, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idView = (EditText) findViewById(R.id.edit_id);
        passwordView = (EditText) findViewById(R.id.edit_password);
        checkAutoLogin = (CheckBox) findViewById(R.id.check_auto_login);

        Button btn = (Button) findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idView.getText().toString();
                password = passwordView.getText().toString();

                NetworkManager.getInstance().login(LoginActivity.this, id, password, new NetworkManager.OnResultListener<LoginResult>() {
                    @Override
                    public void onSuccess(Request request, LoginResult result) {
                        if (result.error == 0) { //success
                            if (checkAutoLogin.isChecked()) { //자동로그인 체크 했다면 키 값 저장 그렇지 않으면 저장X
                                PropertyManager.getInstance().setKey(result.key);
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra(MainActivity.EXTRA_MESSAGE, result.key);
                            startActivity(intent);
                            finish();
                        } else { //로그인 실패 시
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("Login Error")
                                    .setMessage(result.error_message)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Request request, int code, Throwable cause) {

                    }
                });
            }

        });

    }
}

