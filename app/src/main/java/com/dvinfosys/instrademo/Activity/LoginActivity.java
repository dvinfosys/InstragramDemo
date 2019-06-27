package com.dvinfosys.instrademo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dvinfosys.instrademo.R;
import com.dvinfosys.instrademo.Utils.Constanst;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private TextView tvSignUp;
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private String Username, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        init();
    }

    private void init() {
        tvSignUp = findViewById(R.id.link_signup);
        edtUsername = findViewById(R.id.input_email);
        edtPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = edtUsername.getText().toString();
                Password = edtPassword.getText().toString();
                if (Username.isEmpty()) {
                    Toast.makeText(context, "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (Password.isEmpty()) {
                    Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra(Constanst.UserName, Username);
                    i.putExtra(Constanst.Password, Password);
                    startActivity(i);
                }
            }
        });
    }
}
