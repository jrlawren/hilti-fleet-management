package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.AddNewProjectDelegate;
import com.tau.tim.hiltifleetmanagement.Delegates.ProjectManagementMainDelegate;

/**
 * Created by Tim on 11/30/2015.
 */
public class AddNewProject_SaveProject extends AsyncTask<String, String, String>{
    private DBHelper mydb;
    private Context context;
    private AddNewProjectDelegate delegate;

    public AddNewProject_SaveProject(Context context){
        this.context = context;
        delegate = (AddNewProjectDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        String errorMessage = delegate.preSaveProjectCheck();
        return errorMessage;
    }

    protected void onPostExecute(String result){
        if(!result.equals("")){
            delegate.showErrorMessage(result);
            return;
        }else{
            String PN = delegate.getProjectName();
            String startDate = delegate.getStartDate();
            String endDate = delegate.getEndDate();
            boolean isUpdate = delegate.getUpdate();
            boolean isSaved = mydb.saveProject(1, PN, startDate, endDate, isUpdate);
            if(!isSaved){
                delegate.showErrorMessage("Project Name Already Exists");
            }else {
                delegate.finishActivity();
            }
        }
    }
}
