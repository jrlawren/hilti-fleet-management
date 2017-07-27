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
public class ToolAndProjectDetailPopulateScheduleListing extends AsyncTask<String, String, String> {
    private DBHelper mydb;
    private Context context;
    private ToolAndProjectDetailDelegate delegate;
    private ArrayList<? extends Tool> scheduleArrayList;

    public ToolAndProjectDetailPopulateScheduleListing(Context context){
        this.context = context;
        this.delegate = (ToolAndProjectDetailDelegate) context;
        mydb = new DBHelper(this.context);
    }
    @Override
    protected String doInBackground(String... params) {
        scheduleArrayList = mydb.getToolSchedule(params[0], Integer.parseInt(params[1]));
        return null;
    }

    protected void onPostExecute(String result){
        delegate.setScheduleListView(scheduleArrayList);
    }
}
