package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.ProjectDetailsActivityDelegate;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;

/**
 * Created by Tim on 12/1/2015.
 */
public class ProjectDetails_GetProjectFields extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private Project project;
    private Context context;
    private ProjectDetailsActivityDelegate delegate;

    public ProjectDetails_GetProjectFields(Context context){
        this.context = context;
        this.delegate = (ProjectDetailsActivityDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        project = mydb.getProjectName(params[0]);
        return null;
    }

    protected void onPostExecute(String result){
        delegate.updateProjectFields(project);
    }
}
