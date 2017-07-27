package com.tau.tim.hiltifleetmanagement.ProjectManagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tau.tim.hiltifleetmanagement.Adapters.ToolAdapter;
import com.tau.tim.hiltifleetmanagement.Delegates.ProjectDetailsActivityDelegate;
import com.tau.tim.hiltifleetmanagement.Subclasses.ProjectDetails_GetProjectFields;
import com.tau.tim.hiltifleetmanagement.Subclasses.ProjectDetails_GetToolList;
import com.tau.tim.hiltifleetmanagement.ToolManagement.AssignToolActivity;
import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.R;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;
import com.tau.tim.hiltifleetmanagement.ToolAndProjectDetailActivity;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolManagementActivity;

import java.util.ArrayList;

public class ProjectDetailsActivity extends AppCompatActivity implements ProjectDetailsActivityDelegate {
    public static String EDIT_PROJECT = "Edit Project";
    public static String PROJECT_ID = "Project ID";
    private ListView toolListView;
    private TextView projNameTextView;
    private TextView projStartDate;
    private TextView projEndDate;
    //private ArrayList<Tool> toolArrayList;
    //private DBHelper mydb;
    private String projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        Project project;
        Intent intent = getIntent();
        projectID = intent.getStringExtra(ToolManagementActivity.TOOL_PROPERTY);
        String layoutType = intent.getStringExtra(ToolManagementActivity.WHICH_ADAPTER);

        //mydb = new DBHelper(this);
        toolListView = (ListView) findViewById(R.id.projDetailToolListView);
        projNameTextView = (TextView) findViewById(R.id.projDetailProjName);
        projStartDate = (TextView) findViewById(R.id.projDetailStartDate);
        projEndDate = (TextView) findViewById(R.id.projDetailEndDate);

        /*
        project = mydb.getProjectName(projectID);
        projNameTextView.setText(project.getProjectName());
        projStartDate.setText("Start: " + project.getStartDate());
        projEndDate.setText("End:   " + project.getEndDate());
        */

        ProjectDetails_GetProjectFields getProjectFields = new ProjectDetails_GetProjectFields(this);
        getProjectFields.execute(projectID);

        /*
        toolArrayList = mydb.getToolByProject(projectID, "1");

        ToolAdapter toolAdapter = new ToolAdapter(ProjectDetailsActivity.this, toolArrayList, ToolAdapter.LIST_TOOLS);
        toolListView.setAdapter(toolAdapter);
        */

        ProjectDetails_GetToolList getToolList = new ProjectDetails_GetToolList(this);
        getToolList.execute(projectID, "1");

        toolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tool selectedTool = (Tool) parent.getItemAtPosition(position);
                Intent toolIntent = new Intent(ProjectDetailsActivity.this, ToolAndProjectDetailActivity.class);

                toolIntent.putExtra(ToolManagementActivity.TOOL_PROPERTY, selectedTool.getToolName());
                toolIntent.putExtra(ToolManagementActivity.MODEL_NUMBER, selectedTool.getModelNumber());
                toolIntent.putExtra(PROJECT_ID, projectID);
                toolIntent.putExtra(ToolManagementActivity.WHICH_ADAPTER, ToolManagementActivity.TOOL_LISTING);

                startActivity(toolIntent);
            }
         });
    }

    public void assignToolOnClick(View view) {
        Intent assignToolIntent = new Intent(this, AssignToolActivity.class);
        assignToolIntent.putExtra(PROJECT_ID, projectID);
        startActivity(assignToolIntent);
    }

    public void editProjectOnClick(View view){
        Intent editProjectIntent = new Intent(this, AddNewProjectActivity.class);
        editProjectIntent.putExtra(EDIT_PROJECT, projectID);
        startActivity(editProjectIntent);
    }

    @Override
    public void updateProjectFields(Project project) {
        projNameTextView.setText(project.getProjectName());
        projStartDate.setText("Start: " + project.getStartDate());
        projEndDate.setText("End:   " + project.getEndDate());
    }

    @Override
    public void populateToolListView(ArrayList<Tool> toolArrayList) {
        ToolAdapter toolAdapter = new ToolAdapter(ProjectDetailsActivity.this, toolArrayList, ToolAdapter.LIST_TOOLS);
        toolListView.setAdapter(toolAdapter);
    }
}
