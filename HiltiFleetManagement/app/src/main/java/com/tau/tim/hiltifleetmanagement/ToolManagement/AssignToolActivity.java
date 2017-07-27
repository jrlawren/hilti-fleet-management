package com.tau.tim.hiltifleetmanagement.ToolManagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.AssignToolsDelegate;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.ProjectDetailsActivity;
import com.tau.tim.hiltifleetmanagement.R;
import com.tau.tim.hiltifleetmanagement.Subclasses.AssignToolActivity_AssignTool;
import com.tau.tim.hiltifleetmanagement.Subclasses.AssignToolActivity_GetProjectName;
import com.tau.tim.hiltifleetmanagement.Subclasses.AssignToolActivity_GetTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AssignToolActivity extends AppCompatActivity implements View.OnClickListener, AssignToolsDelegate{
    private TextView projectName;
    private EditText startDateET;
    private EditText endDateET;
    private Spinner toolSpinner;
    private Project project;

    private DatePickerDialog startDateDialog;
    private DatePickerDialog endDateDialog;

    private SimpleDateFormat dateFormatter;

    private String projectID;

    private ArrayList<Tool> toolArrayList;

    //private DBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_tool);

        //mydb = new DBHelper(this);

        projectName = (TextView) findViewById(R.id.assignToolProjName);
        toolSpinner = (Spinner) findViewById(R.id.assignToolSpinner);

        Intent intent = getIntent();
        projectID = intent.getStringExtra(ProjectDetailsActivity.PROJECT_ID);

        /*
        project = mydb.getProjectName(projectID);
        projectName.setText(project.getProjectName());


        tempToolArrayList = new ArrayList<>();
        tempToolArrayList = mydb.getTools("", 1);
        ArrayList<String> toolArrayList = new ArrayList<>();

        for (Tool tool:tempToolArrayList) {
            toolArrayList.add(tool.getToolName() + " v." + tool.getModelNumber());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner, toolArrayList);
        toolSpinner.setAdapter(spinnerAdapter);
        */

        AssignToolActivity_GetProjectName getProjectName = new AssignToolActivity_GetProjectName(this);
        getProjectName.execute(projectID);

        AssignToolActivity_GetTools getTools = new AssignToolActivity_GetTools(this);
        getTools.execute();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        //projectName = (EditText) findViewById(R.id.assig);
        startDateET = (EditText) findViewById(R.id.assignToolStartDate);
        endDateET = (EditText) findViewById(R.id.assignToolEndDate);

        startDateET.setInputType(InputType.TYPE_NULL);
        endDateET.setInputType(InputType.TYPE_NULL);

        setDate();
    }

    public void setDate(){
        startDateET.setOnClickListener(this);
        endDateET.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        startDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDateET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDateET.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == startDateET){
            startDateDialog.show();
        }else if(view == endDateET){
            endDateDialog.show();
        }
    }

    public void saveAssignment(View view) {
        AssignToolActivity_AssignTool assignTool = new AssignToolActivity_AssignTool(this);
        int selectedTool = toolSpinner.getSelectedItemPosition();
        assignTool.execute(projectID, toolArrayList.get(selectedTool).getToolName(), toolArrayList.get(selectedTool).getModelNumber(),
                startDateET.getText().toString(), endDateET.getText().toString());
        /*
        int selectedTool = 0;
        String toolName = "";
        String modelNumber = "";
        String startDate = "";
        String endDate = "";

        selectedTool = toolSpinner.getSelectedItemPosition();
        toolName = toolArrayList.get(selectedTool).getToolName();
        modelNumber = toolArrayList.get(selectedTool).getModelNumber();
        startDate = startDateET.getText().toString();
        endDate = endDateET.getText().toString();

        int sd;
        int ed;
        if(startDate.equals("")) {
            Toast.makeText(getApplicationContext(), "ERROR: MISSING START DATE", Toast.LENGTH_SHORT).show();
            return;
        } else {
            startDate = startDate.substring(0,4)+startDate.substring(5,7)+startDate.substring(8,10);
            sd = Integer.parseInt(startDate);
        }
        if(endDate.equals("")) {
            Toast.makeText(getApplicationContext(), "ERROR: MISSING END DATE", Toast.LENGTH_SHORT).show();
            return;
        } else {
            endDate = endDate.substring(0,4)+endDate.substring(5,7)+endDate.substring(8,10);
            ed = Integer.parseInt(endDate);
        }

        String SD = project.getStartDate();
        SD = SD.substring(0,4)+SD.substring(5,7)+SD.substring(8,10);
        int start = Integer.parseInt(SD);
        String ED = project.getEndDate();
        ED = ED.substring(0,4)+ED.substring(5,7)+ED.substring(8,10);
        int end = Integer.parseInt(ED);

        if (ed < sd) {
            Toast.makeText(getApplicationContext(), "DATE ERROR", Toast.LENGTH_SHORT).show();
        }else if ((sd < start)||(sd > end)) {
            Toast.makeText(getApplicationContext(), "ERROR: START DATE OUT OF BOUNDS", Toast.LENGTH_SHORT).show();
        }else if ((ed < start)||(ed > end)) {
            Toast.makeText(getApplicationContext(), "ERROR: END DATE OUT OF BOUNDS", Toast.LENGTH_SHORT).show();
        }  else {
            if (mydb.assignTool(projectID, toolName, modelNumber, startDate, endDate)) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ERROR: NO AVAILABLE TOOLS OF SELECTED TYPE", Toast.LENGTH_SHORT).show();
            }
        }
        */
    }

    public void cancelAssignment(View view) {
        this.finish();
    }

    @Override
    public void populateProjectInfo(Project project) {
        this.project = project;
        projectName.setText(project.getProjectName());
    }

    @Override
    public void populateToolListView(ArrayList<String> toolNameArrayList, ArrayList<Tool> toolArrayList) {
        this.toolArrayList = toolArrayList;
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner, toolNameArrayList);
        toolSpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public Project getProject() {
        return project;
    }

    public void displayMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void finishActivity(){
        Log.d("Testing", "Here");
        finish();
    }
}
