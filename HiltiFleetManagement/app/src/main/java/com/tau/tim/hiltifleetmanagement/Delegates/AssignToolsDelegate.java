package com.tau.tim.hiltifleetmanagement.Delegates;

import com.tau.tim.hiltifleetmanagement.ProjectManagement.Project;
import com.tau.tim.hiltifleetmanagement.ToolManagement.Tool;

import java.util.ArrayList;

/**
 * Created by Tim on 12/1/2015.
 */
public interface AssignToolsDelegate {
    public void populateProjectInfo(Project project);
    public void populateToolListView(ArrayList<String> toolNameArrayList, ArrayList<Tool> toolArrayList);
    //public String preAssignmentCheck();
    public Project getProject();
    public void displayMessage(String message);
    public void finishActivity();
}
