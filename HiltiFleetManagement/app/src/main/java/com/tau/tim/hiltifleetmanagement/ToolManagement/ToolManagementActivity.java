package com.tau.tim.hiltifleetmanagement.ToolManagement;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tau.tim.hiltifleetmanagement.Adapters.ToolAdapter;
import com.tau.tim.hiltifleetmanagement.DBHelper;
import com.tau.tim.hiltifleetmanagement.Delegates.ToolManagementDelegate;
import com.tau.tim.hiltifleetmanagement.R;
import com.tau.tim.hiltifleetmanagement.Subclasses.ToolManagement_GetToolID;
import com.tau.tim.hiltifleetmanagement.Subclasses.ToolManagement_GetTools;
import com.tau.tim.hiltifleetmanagement.ToolAndProjectDetailActivity;

import java.util.ArrayList;

public class ToolManagementActivity extends AppCompatActivity implements ToolManagementDelegate{
    public static String TOOL_LISTING = "Tool Listing";
    public static String TOOL_SCHEDULE = "Tool Schedule";
    public static String TOOL_PROPERTY = "Tool Property";
    public static String WHICH_ADAPTER = "Which Adapter";
    public static String MODEL_NUMBER = "Model Number";

    private DBHelper mydb = new DBHelper(this);
    private TextView searchTextView;
    private Spinner searchSpinner;
    private ArrayList<Tool> toolArrayList;
    private ListView toolListView;
    private ToolAdapter toolAdapter;
    private String whichActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_management);

        ArrayAdapter<CharSequence> searchAdapter = ArrayAdapter.createFromResource(this, R.array.searchOptions, android.R.layout.simple_spinner_item);
        searchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        searchSpinner = (Spinner) findViewById(R.id.sortSpinner);
        searchSpinner.setAdapter(searchAdapter);
        searchTextView = (TextView) findViewById(R.id.searchEditText);
        toolListView = (ListView) findViewById(R.id.toolListView);

        searchTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    if(searchSpinner.getSelectedItem().equals("Tool Name")){
                        String toolName = searchTextView.getText().toString().trim();
                        /*)
                        toolArrayList = mydb.getTools(searchTextView.getText().toString(), 1);
                        toolAdapter = new ToolAdapter(ToolManagementActivity.this, toolArrayList, ToolAdapter.LIST_TOOLS);
                        toolListView.setAdapter(toolAdapter);
                        whichActivity = "Tool Name";
                        */
                        ToolManagement_GetTools getTools = new ToolManagement_GetTools(ToolManagementActivity.this);
                        getTools.execute(toolName, "1");
                        InputMethodManager inputMethodManager = (InputMethodManager) ToolManagementActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(ToolManagementActivity.this.getCurrentFocus().getWindowToken(), 0);
                        return true;
                    }else if(searchSpinner.getSelectedItem().equals("Tool ID")){
                        String toolID = searchTextView.getText().toString().trim();
                        /*
                        toolArrayList = mydb.getToolID(searchTextView.getText().toString(), 1, DBHelper.SEARCH_LIKE);
                        toolAdapter = new ToolAdapter(ToolManagementActivity.this, toolArrayList, ToolAdapter.LIST_TOOL_DETAILS);
                        toolListView.setAdapter(toolAdapter);
                        whichActivity = "Tool ID";
                        */
                        ToolManagement_GetToolID getToolID = new ToolManagement_GetToolID(ToolManagementActivity.this);
                        getToolID.execute(toolID, "1");
                        InputMethodManager inputMethodManager = (InputMethodManager) ToolManagementActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(ToolManagementActivity.this.getCurrentFocus().getWindowToken(), 0);
                        return true;
                    }

                }
                return false;
            }
        });

        toolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Tool selectedTool = new Tool();
            Intent toolIntent = new Intent(ToolManagementActivity.this, ToolAndProjectDetailActivity.class);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTool = (Tool) parent.getItemAtPosition(position);
                if (whichActivity.equals("Tool Name")){
                    toolIntent.putExtra(TOOL_PROPERTY,  selectedTool.getToolName());
                    toolIntent.putExtra(MODEL_NUMBER, selectedTool.getModelNumber());
                    toolIntent.putExtra(WHICH_ADAPTER, TOOL_LISTING);
                }else if(whichActivity.equals("Tool ID")){
                    toolIntent.putExtra(TOOL_PROPERTY, Integer.toString(selectedTool.getToolID()));
                    toolIntent.putExtra(WHICH_ADAPTER, TOOL_SCHEDULE);
                }
                startActivity(toolIntent);
            }
        });
    }

    @Override
    public void updateToolList(ArrayList<Tool> toolArrayList, String activity, String whichAdapter) {
        toolAdapter = new ToolAdapter(ToolManagementActivity.this, toolArrayList, whichAdapter);
        toolListView.setAdapter(toolAdapter);
        whichActivity = activity;
    }
}