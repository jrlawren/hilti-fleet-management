package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.ProjectManagementMainDelegate;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;

import java.util.ArrayList;

/**
 * Created by Tim on 11/30/2015.
 */
public class ProjectManagementMain extends AsyncTask<String, String, String> {
    private Context context;
    private ProjectManagementMainDelegate delegate;
    private DBHelper mydb;
    private ArrayList<Project> projectArrayList;

    public ProjectManagementMain(Context context){
        this.context = context;
        this.delegate = (ProjectManagementMainDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        projectArrayList = mydb.getProjects(params[0]);
        return null;
    }

    protected void onPostExecute(String result){
        delegate.populateProjectListView(projectArrayList);
    }
}
