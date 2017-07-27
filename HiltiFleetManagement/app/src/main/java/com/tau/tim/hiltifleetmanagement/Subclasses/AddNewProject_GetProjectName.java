package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.AddNewProjectDelegate;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;

/**
 * Created by Tim on 11/30/2015.
 */
public class AddNewProject_GetProjectName extends AsyncTask<String, String, String>{
    private AddNewProjectDelegate delegate;
    private Context context;
    private DBHelper mydb;
    private Project project;

    public AddNewProject_GetProjectName(Context context){
        this.context = context;
        this.delegate = (AddNewProjectDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        project = mydb.getProjectName(params[0]);
        return null;
    }

    protected void onPostExecute(String result){
        delegate.populateProjectName(project);
    }
}
