package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.AssignToolsDelegate;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;

/**
 * Created by Tim on 12/1/2015.
 */
public class AssignToolActivity_AssignTool extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private Context context;
    private AssignToolsDelegate delegate;
    private String projectID;
    private String toolName;
    private String modelNumber;
    private String startDate;
    private String endDate;


    public AssignToolActivity_AssignTool(Context context){
        this.context = context;
        this.delegate = (AssignToolsDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        this.projectID = params[0];
        this.toolName = params[1];
        this.modelNumber = params[2];
        this.startDate = params[3];
        this.endDate = params[4];

        String errorMessage = preAssignmentCheck();
        return errorMessage;
    }

    protected void onPostExecute(String results){
        boolean saveSuccessful = false;

        if(!results.equals("")){
            delegate.displayMessage(results);
            return;
        }else{
            saveSuccessful = mydb.assignTool(projectID, toolName, modelNumber, startDate, endDate);
        }
        if(saveSuccessful){
            delegate.displayMessage("Assignment Successful");
            delegate.finishActivity();
        }else {
            delegate.displayMessage("No Tools Available of the Selected Type");
        }
    }

    private String preAssignmentCheck(){
        Project project = delegate.getProject();

        int sd;
        int ed;
        if(startDate.equals("")) {
            //Toast.makeText(getApplicationContext(), "ERROR: MISSING START DATE", Toast.LENGTH_SHORT).show();
            return "Error: Missing Start Date";
        } else {
            startDate = startDate.substring(0,4)+startDate.substring(5,7)+startDate.substring(8,10);
            sd = Integer.parseInt(startDate);
        }
        if(endDate.equals("")) {
            //Toast.makeText(getApplicationContext(), "ERROR: MISSING END DATE", Toast.LENGTH_SHORT).show();
            return "Error: Missing End Date";
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
            //Toast.makeText(getApplicationContext(), "DATE ERROR", Toast.LENGTH_SHORT).show();
            return "End Date cannot be less than Start Date";
        }else if ((sd < start)||(sd > end)) {
            //Toast.makeText(getApplicationContext(), "ERROR: START DATE OUT OF BOUNDS", Toast.LENGTH_SHORT).show();
            return "Tool's Start Date is out of Project's date bounds";
        }else if ((ed < start)||(ed > end)) {
            //Toast.makeText(getApplicationContext(), "ERROR: END DATE OUT OF BOUNDS", Toast.LENGTH_SHORT).show();
            return "Tool's End Date is out of Project's date bounds";
        }
        return "";
    }
}
