package com.tau.tim.hiltifleetmanagement.RepairServices;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.R;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;


public class RepairServiceActivity extends AppCompatActivity {

    DBHelper mydb;
    Button TakePic;
    Button send;
    ImageView Pic;
    private static final int CAM_REQUEST = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_service);

        mydb = new DBHelper(this);

        TakePic = (Button) findViewById(R.id.TakePicButton);
        TakePic.setOnClickListener(new TakePicClicker());

        Pic = (ImageView) findViewById(R.id.Picture);

        send = (Button) findViewById(R.id.SendReqButton);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("SendMailActivity", "Send Button Clicked.");
                EditText toolid = (EditText) findViewById(R.id.ToolIDField);
                String Toolid = toolid.getText().toString();
                if (mydb.getToolID(Toolid, 1, mydb.SEARCH_UNIQUE).isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ERROR: TOOL DOES NOT EXIST", Toast.LENGTH_SHORT).show();
                } else {
                    EditText estdam = (EditText) findViewById(R.id.EstDmgField);
                    String Estdam = estdam.getText().toString();
                    String fromEmail = mydb.getEmail();
                    String fromPassword = mydb.getPassword();
                    String toEmails = "hiltitestemail777@gmail.com";
                    List toEmailList = Arrays.asList(toEmails.split("\\s*,\\s*"));
                    Log.i("SendMailActivity", "To List: " + toEmailList);
                    String emailSubject = "Tool Repair Request";
                    String emailBody = "Tool ID:" + Toolid;
                    emailBody = emailBody + "\n Estimated Damages:" + Estdam;
                    new SendMailTask(RepairServiceActivity.this).execute(fromEmail,
                            fromPassword, toEmailList, emailSubject, emailBody);
                    mydb.updateToolStatus(Toolid);
                    finish();
                }
            }
        });
    }

    class TakePicClicker implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((resultCode == RESULT_OK)&&(requestCode == CAM_REQUEST)) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Pic.setImageBitmap(thumbnail);
        }
    }
}