package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.AssignToolsDelegate;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;

/**
 * Created by Tim on 12/1/2015.
 */
public class AssignToolActivity_GetProjectName extends AsyncTask<String, String, String>{
    private DBHelper mydb;
    private Context context;
    private AssignToolsDelegate delegate;
    private Project project;

    public AssignToolActivity_GetProjectName(Context context){
        this.context = context;
        this.delegate = (AssignToolsDelegate) context;
        mydb = new DBHelper(this.context);
    }
    @Override
    protected String doInBackground(String... params) {
        project = mydb.getProjectName(params[0]);
        return null;
    }

    protected void onPostExecute(String results){
        delegate.populateProjectInfo(project);
    }
}
