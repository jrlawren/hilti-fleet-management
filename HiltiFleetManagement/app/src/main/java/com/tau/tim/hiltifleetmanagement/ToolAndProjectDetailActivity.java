package com.tau.tim.hiltifleetmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tau.tim.hiltifleetmanagement.Adapters.ToolAdapter;
import com.tau.tim.hiltifleetmanagement.Delegates.ToolAndProjectDetailDelegate;
import com.tau.tim.hiltifleetmanagement.ProjectManagement.ProjectDetailsActivity;
import com.tau.tim.hiltifleetmanagement.Subclasses.ToolAndProjectDetailPopulateScheduleListing;
import com.tau.tim.hiltifleetmanagement.Subclasses.ToolAndProjectDetailPopulateToolListing;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolManagementActivity;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolSchedule;

import java.util.ArrayList;

public class ToolAndProjectDetailActivity extends AppCompatActivity implements ToolAndProjectDetailDelegate{
    private DBHelper myDB = new DBHelper(this);
    private ListView itemListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent toolIntent = getIntent();
        String whichAdapter = toolIntent.getStringExtra(ToolManagementActivity.WHICH_ADAPTER);
        String toolProperty = toolIntent.getStringExtra(ToolManagementActivity.TOOL_PROPERTY);

        TextView mainLabel;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_and_project_detail);

        itemListing = (ListView) findViewById(R.id.itemListView);
        mainLabel = (TextView) findViewById(R.id.mainLabel);

        if(whichAdapter.equals(ToolManagementActivity.TOOL_LISTING)){
            String modelNumber = toolIntent.getStringExtra(ToolManagementActivity.MODEL_NUMBER);
            String projectID = "";

            if(toolIntent.hasExtra(ProjectDetailsActivity.PROJECT_ID)){
                projectID = toolIntent.getStringExtra(ProjectDetailsActivity.PROJECT_ID);
            }

            mainLabel.setText("Tools Listing");

            ToolAndProjectDetailPopulateToolListing toolListing = new ToolAndProjectDetailPopulateToolListing(this);
            toolListing.execute(toolProperty, modelNumber, "1", projectID);

            //ArrayList<Tool> itemArrayList = new ArrayList<Tool>(myDB.getToolByName(toolProperty, modelNumber, 1, projectID));
            //itemListing.setAdapter(new ToolAdapter(this, itemArrayList, ToolAdapter.LIST_TOOL_DETAILS));
        }else if(whichAdapter.equals(ToolManagementActivity.TOOL_SCHEDULE)){
            mainLabel.setText("Tool Schedules");

            ToolAndProjectDetailPopulateScheduleListing scheduleListing = new ToolAndProjectDetailPopulateScheduleListing(this);
            scheduleListing.execute(toolProperty, "1");

            //ArrayList<? extends Tool> scheduleArrayList = new ArrayList<ToolSchedule>(myDB.getToolSchedule(toolProperty, 1));
            //itemListing.setAdapter(new ToolAdapter(this, (ArrayList<Tool>) scheduleArrayList, ToolAdapter.LIST_PROJECTS));
        }

        if(whichAdapter.equals(ToolManagementActivity.TOOL_LISTING)){
            itemListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Tool selectedTool = new Tool();
                    selectedTool = (Tool)parent.getItemAtPosition(position);
                    Intent toolIntent = new Intent(ToolAndProjectDetailActivity.this, ToolAndProjectDetailActivity.class);
                    toolIntent.putExtra(ToolManagementActivity.TOOL_PROPERTY, Integer.toString(selectedTool.getToolID()));
                    toolIntent.putExtra(ToolManagementActivity.WHICH_ADAPTER, ToolManagementActivity.TOOL_SCHEDULE);
                    startActivity(toolIntent);
                }
            });
        }
    }


    @Override
    public void setToolListView(ArrayList<Tool> toolArrayList) {
        itemListing.setAdapter(new ToolAdapter(this, toolArrayList, ToolAdapter.LIST_TOOL_DETAILS));
    }

    @Override
    public void setScheduleListView(ArrayList<? extends Tool> scheduleArrayList) {
        itemListing.setAdapter(new ToolAdapter(this, (ArrayList<Tool>) scheduleArrayList, ToolAdapter.LIST_PROJECTS));
    }
}
