package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.AssignToolsDelegate;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public class AssignToolActivity_GetTools extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private Context context;
    private AssignToolsDelegate delegate;
    private ArrayList<Tool> toolArrayList;

    public AssignToolActivity_GetTools(Context context){
        this.context = context;
        this.delegate = (AssignToolsDelegate) context;
        mydb = new DBHelper(this.context);
    }
    @Override
    protected String doInBackground(String... params) {
        toolArrayList = mydb.getTools("", 1);
        return null;
    }

    protected void onPostExecute(String results){
        ArrayList<String> toolNameArrayList = new ArrayList<>();

        for (Tool tool:toolArrayList) {
            toolNameArrayList.add(tool.getToolName() + " v." + tool.getModelNumber());
        }

        delegate.populateToolListView(toolNameArrayList, toolArrayList);
    }
}
