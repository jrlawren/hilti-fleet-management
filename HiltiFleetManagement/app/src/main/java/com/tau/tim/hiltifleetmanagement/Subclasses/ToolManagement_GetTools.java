package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.Adapters.ToolAdapter;
import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.ToolManagementDelegate;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public class ToolManagement_GetTools extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private Context context;
    private ToolManagementDelegate delegate;
    private ArrayList<Tool> toolArrayList;

    public ToolManagement_GetTools(Context context){
        this.context = context;
        this.delegate = (ToolManagementDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        toolArrayList = mydb.getTools(params[0], Integer.parseInt(params[1]));
        return null;
    }

    protected void onPostExecute(String results){
        delegate.updateToolList(toolArrayList, "Tool Name", ToolAdapter.LIST_TOOLS);
    }
}
