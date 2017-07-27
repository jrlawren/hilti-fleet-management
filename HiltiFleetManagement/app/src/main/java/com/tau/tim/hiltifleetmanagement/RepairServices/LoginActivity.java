package com.tau.tim.hiltifleetmanagement.RepairServices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.MainActivity;
import com.tau.tim.hiltifleetmanagement.R;

public class LoginActivity extends AppCompatActivity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mydb = new DBHelper(this);
    }

    public void logIn(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        String user = username.getText().toString().trim();
        EditText password = (EditText) findViewById(R.id.password);
        String pass = password.getText().toString().trim();
        int Case = mydb.getCred(user, pass);
        if(Case == 0) {
            Intent login = new Intent(this, MainActivity.class);
            startActivity(login);
        } else if (Case == 1){
            Toast.makeText(getApplicationContext(), "ERROR: INCORRECT PASSWORD", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "ERROR: INCORRECT USERNAME", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "PLEASE ENTER LOG IN INFORMATION", Toast.LENGTH_SHORT).show();
    }
}