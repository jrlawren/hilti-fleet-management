package com.tau.tim.hiltifleetmanagement.Subclasses;

import android.content.Context;
import android.os.AsyncTask;

import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.ToolAndProjectDetailDelegate;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public class ToolAndProjectDetailPopulateToolListing extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private Context context;
    private ToolAndProjectDetailDelegate delegate;
    private ArrayList<Tool> toolArrayList;

    public ToolAndProjectDetailPopulateToolListing(Context context){
        this.context = context;
        this.delegate = (ToolAndProjectDetailDelegate) context;
        mydb = new DBHelper(this.context);
    }
    @Override
    protected String doInBackground(String... params) {
        toolArrayList = mydb.getToolByName(params[0], params[1], Integer.parseInt(params[2]), params[3]);
        return null;
    }

    protected void onPostExecute(String result){
        delegate.setToolListView(toolArrayList);
    }
}
