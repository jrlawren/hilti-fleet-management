package com.tau.tim.hiltifleetmanagement.ProjectManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.tau.tim.hiltifleetmanagement.Adapters.ProjectAdapter;
import com.tau.tim.hiltifleetmanagement.Delegates.ProjectManagementMainDelegate;
import com.tau.tim.hiltifleetmanagement.R;
import com.tau.tim.hiltifleetmanagement.Subclasses.ProjectManagementMain;
import com.tau.tim.hiltifleetmanagement.ToolManagement.ToolManagementActivity;

import java.util.ArrayList;

public class ProjectManagementMainActivity extends AppCompatActivity implements ProjectManagementMainDelegate {
    //DBHelper mydb;
    ListView projectListView;
    //Spinner sortSpinner;
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_management_main);
        Intent intent = getIntent();

        searchEditText = (EditText) findViewById(R.id.searchField);
        projectListView = (ListView) findViewById(R.id.projectListView);
        //mydb = new DBHelper(this);

        projectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Project selectedProject = (Project) parent.getItemAtPosition(position);
                int projectID = selectedProject.getProjectID();
                Intent projectIntent = new Intent(ProjectManagementMainActivity.this, ProjectDetailsActivity.class);
                projectIntent.putExtra(ToolManagementActivity.TOOL_PROPERTY, Integer.toString(projectID));
                projectIntent.putExtra(ToolManagementActivity.WHICH_ADAPTER, ToolManagementActivity.TOOL_LISTING);
                startActivity(projectIntent);
            }
        });

        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ProjectManagementMain populateListView = new ProjectManagementMain(ProjectManagementMainActivity.this);
                    populateListView.execute(searchEditText.getText().toString().trim());
                    InputMethodManager inputMethodManager = (InputMethodManager) ProjectManagementMainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(ProjectManagementMainActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    public void addNewProject(View view) {
        Intent intent = new Intent(this, AddNewProjectActivity.class);
        startActivity(intent);
    }

    @Override
    public void populateProjectListView(ArrayList<Project> projectArrayList) {
        ProjectAdapter adapter = new ProjectAdapter(this, projectArrayList);
        projectListView.setAdapter(adapter);
    }
}
