package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.ProjectDetailsActivityDelegate;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public class ProjectDetails_GetToolList extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private ArrayList<Tool> toolArrayList;
    private Context context;
    private ProjectDetailsActivityDelegate delegate;

    public ProjectDetails_GetToolList(Context context){
        this.context = context;
        this.delegate = (ProjectDetailsActivityDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        String projectID = params[0];
        String clientID = params[1];
        toolArrayList = mydb.getToolByProject(projectID, clientID);
        return null;
    }

    protected void onPostExecute(String results){
        delegate.populateToolListView(toolArrayList);
    }
}
