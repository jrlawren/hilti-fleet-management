package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.Adapters.ToolAdapter;
import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.ToolManagementDelegate;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public class ToolManagement_GetToolID extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private Context context;
    private ToolManagementDelegate delegate;
    private ArrayList<Tool> toolArrayList;

    public ToolManagement_GetToolID(Context context){
        this.context = context;
        this.delegate = (ToolManagementDelegate) context;
        mydb = new DBHelper(this.context);
    }

    @Override
    protected String doInBackground(String... params) {
        toolArrayList = mydb.getToolID(params[0], Integer.parseInt(params[1]), DBHelper.SEARCH_LIKE);
        return null;
    }

    protected void onPostExecute(String results){
        delegate.updateToolList(toolArrayList, "Tool ID", ToolAdapter.LIST_TOOL_DETAILS);
    }
}
