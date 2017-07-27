package com.tau.tim.hiltifleetmanagement.ProjectManagement;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.AddNewProjectDelegate;
import com.tau.tim.hiltifleetmanagement.R;
import com.tau.tim.hiltifleetmanagement.Subclasses.AddNewProject_GetProjectName;
import com.tau.tim.hiltifleetmanagement.Subclasses.AddNewProject_SaveProject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewProjectActivity extends AppCompatActivity implements View.OnClickListener, AddNewProjectDelegate {

    private EditText projectName;
    private EditText projectDescr;
    private EditText startDate;
    private EditText endDate;

    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;

    private SimpleDateFormat dateFormatter;

    private DBHelper mydb;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_project);

        String projectID = "";

        mydb = new DBHelper(this);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        projectName = (EditText) findViewById(R.id.projectNameTextfield);
        startDate = (EditText) findViewById(R.id.startDateTextField);
        endDate = (EditText) findViewById(R.id.endDateTextField);

        startDate.setInputType(InputType.TYPE_NULL);
        endDate.setInputType(InputType.TYPE_NULL);

        Intent intent = getIntent();
        if(intent.hasExtra(ProjectDetailsActivity.EDIT_PROJECT)){
            projectID = intent.getStringExtra(ProjectDetailsActivity.EDIT_PROJECT);
            /*
            Project project = new Project();
            project = mydb.getProjectName(projectID);
            projectName.setText(project.getProjectName());
            projectName.setEnabled(false);
            startDate.setText(project.getStartDate());
            endDate.setText(project.getEndDate());
            isUpdate = true;*/

            AddNewProject_GetProjectName getProjectName = new AddNewProject_GetProjectName(this);
            getProjectName.execute(projectID);
            isUpdate = true;
        }

        setDate();
    }

    public void setDate(){
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        startDateDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endDateDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == startDate){
            startDateDialog.show();
        }else if(view == endDate){
            endDateDialog.show();
        }
    }

    public void saveProject(View view) {
        /*
        String PN = projectName.getText().toString();
        String SD = startDate.getText().toString();

        if(!SD.equals("")) {
            SD = SD.substring(0, 3) + SD.substring(5, 6) + SD.substring(8, 9);
        }else{
            Toast.makeText(getApplicationContext(), "MISSING START DATE", Toast.LENGTH_SHORT).show();
            return;
        }

        int sd = Integer.parseInt(SD);
        String ED = endDate.getText().toString();

        if(!ED.equals("")){
            ED = ED.substring(0,3)+ED.substring(5,6)+ED.substring(8, 9);
        }else{
            Toast.makeText(getApplicationContext(), "MISSING END DATE", Toast.LENGTH_SHORT).show();
            return;
        }

        int ed = Integer.parseInt(ED);
        if (PN.equals(null)||SD.equals(null)||ED.equals(null)) {
            Toast.makeText(getApplicationContext(), "MISSING FIELD ERROR", Toast.LENGTH_SHORT).show();
        } else if (ed < sd) {
            Toast.makeText(getApplicationContext(), "DATE ERROR", Toast.LENGTH_SHORT).show();
        } else {
            if (mydb.saveProject(1, PN.trim(), startDate.getText().toString(), endDate.getText().toString(), isUpdate)) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ERROR: PROJECT ALREADY EXISTS", Toast.LENGTH_SHORT).show();
            }
        }
        */

        AddNewProject_SaveProject saveProject = new AddNewProject_SaveProject(this);
        saveProject.execute();
    }

    @Override
    public void populateProjectName(Project project) {
        projectName.setText(project.getProjectName());
        projectName.setEnabled(false);
        startDate.setText(project.getStartDate());
        endDate.setText(project.getEndDate());
        isUpdate = true;
    }

    @Override
    public void saveProjectThread(String errorMessage) {

    }

    public String preSaveProjectCheck(){
        String PN = projectName.getText().toString();
        String SD = startDate.getText().toString();

        if(!SD.equals("")) {
            SD = SD.substring(0, 3) + SD.substring(5, 6) + SD.substring(8, 9);
        }else{
            //Toast.makeText(getApplicationContext(), "MISSING START DATE", Toast.LENGTH_SHORT).show();
            return "Missing Start Date";
        }

        int sd = Integer.parseInt(SD);
        String ED = endDate.getText().toString();

        if(!ED.equals("")){
            ED = ED.substring(0,3)+ED.substring(5,6)+ED.substring(8, 9);
        }else{
            //Toast.makeText(getApplicationContext(), "MISSING END DATE", Toast.LENGTH_SHORT).show();
            return "Missing End Date";
        }

        int ed = Integer.parseInt(ED);
        if (PN.equals(null)||SD.equals(null)||ED.equals(null)) {
            //Toast.makeText(getApplicationContext(), "MISSING FIELD ERROR", Toast.LENGTH_SHORT).show();
            return "Missing Field Error";
        } else if (ed < sd) {
            //Toast.makeText(getApplicationContext(), "DATE ERROR", Toast.LENGTH_SHORT).show();
            return "End Date cannot be less than Start Date";
        }
        return "";
    }

    public void showErrorMessage(String errorMessage){
        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public String getProjectName() {
        return projectName.getText().toString().trim();
    }

    @Override
    public String getStartDate() {
        return startDate.getText().toString();
    }

    @Override
    public String getEndDate() {
        return endDate.getText().toString();
    }

    @Override
    public boolean getUpdate() {
        return isUpdate;
    }

    public void finishActivity(){
        Toast.makeText(getApplicationContext(), "Project Saved Successfully", Toast.LENGTH_LONG).show();
        finish();
    }

    public void cancelProject(View view) {
        finish();
    }
}
